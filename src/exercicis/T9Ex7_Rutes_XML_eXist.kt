package exercicis



import javax.swing.JFrame
import java.awt.EventQueue
import javax.swing.JLabel
import javax.swing.JTextField
import javax.swing.JTable
import javax.swing.JButton
import javax.swing.JPanel
import java.awt.GridLayout
import javax.swing.BoxLayout
import javax.swing.JScrollPane
import java.awt.FlowLayout
import org.w3c.dom.Element
import javax.xml.xquery.XQConnection
import javax.xml.xquery.XQResultSequence
import net.xqj.exist.ExistXQDataSource
import javax.xml.xquery.XQConstants
import kotlin.system.exitProcess


class T9Ex7_Rutes_XML_eXist : JFrame() {

    //Variables de conexion
    val dataSource = ExistXQDataSource()
    var connection: XQConnection? = null

    var rs: XQResultSequence? = null

    val etiqueta = JLabel("")
    val etNom = JLabel("Ruta:")
    val qNom = JTextField(15)
    val etDesn = JLabel("Desnivell:")
    val qDesn = JTextField(5)
    val etDesnAcum = JLabel("Desnivell acumulat:")
    val qDesnAcum = JTextField(5)
    val etPunts = JLabel("Punts:")
    val punts = JTable(1, 3)
    val primer = JButton("<<")
    val anterior = JButton("<")
    val seguent = JButton(">")
    val ultim = JButton(">>")
    val tancar = JButton("Tancar")

    init {
        //Propiedad de acceso para la fuente
        dataSource.setProperty("serverName", "89.36.214.106")
        //Instanciamos conexion
        connection = dataSource.connection

        getContentPane().setLayout(GridLayout(0, 1))
        val p_prin = JPanel()
        p_prin.setLayout(BoxLayout(p_prin, BoxLayout.Y_AXIS))

        val panell1 = JPanel(GridLayout(0, 2))
        panell1.add(etNom)
        qNom.setEditable(false)
        panell1.add(qNom)
        panell1.add(etDesn)
        qDesn.setEditable(false)
        panell1.add(qDesn)
        panell1.add(etDesnAcum)
        qDesnAcum.setEditable(false)
        panell1.add(qDesnAcum)
        panell1.add(etPunts)

        val panell2 = JPanel(GridLayout(0, 1))
        punts.setEnabled(false)
        val scroll = JScrollPane(punts)
        panell2.add(scroll, null)

        val panell5 = JPanel(FlowLayout())
        panell5.add(primer)
        panell5.add(anterior)
        panell5.add(seguent)
        panell5.add(ultim)
        panell5.add(tancar)

        contentPane.add(p_prin)
        p_prin.add(panell1)
        p_prin.add(panell2)
        p_prin.add(panell5)

        setVisible(true)
        pack()

        primer.addActionListener() {
            rs?.first()
            mostrarRuta()
            controlBotons()
        }

        anterior.addActionListener() {
            rs?.previous()
            mostrarRuta()
            controlBotons()
        }

        seguent.addActionListener() {
            rs?.next()
            mostrarRuta()
            controlBotons()
        }

        ultim.addActionListener() {
            rs?.last()
            mostrarRuta()
            controlBotons()
        }

        tancar.addActionListener() {
            connection!!.close()
            exitProcess(0);
        }

        inicialitzar()
        mostrarRuta()
    }

    fun plenarTaula(elementPunts: Element) {

        val punts = elementPunts.getElementsByTagName("punt")
        val tabla = Array(punts.length) { Array(3) { "" } }
        for (i in 0 until punts.length) {
            val punt = punts.item(i) as Element
            tabla[i][0] = punt.getElementsByTagName("nom").item(0).firstChild.nodeValue
            tabla[i][1] = punt.getElementsByTagName("latitud").item(0).firstChild.nodeValue
            tabla[i][2] = punt.getElementsByTagName("longitud").item(0).firstChild.nodeValue
        }
        val campos = arrayOf<String>("Nom punt", "Latitud", "Longitud")
        this.punts.model = javax.swing.table.DefaultTableModel(tabla, campos)
    }

    fun inicialitzar() {
        val sent = "for \$ruta in doc(\"/db/Tema9/Rutes.xml\")//ruta return \$ruta"

        val context = connection!!.getStaticContext()
        context.setScrollability(XQConstants.SCROLLTYPE_SCROLLABLE)
        connection!!.setStaticContext(context)

        rs = connection!!.prepareExpression(sent).executeQuery()

        rs?.first()
    }

    /**
     * Asigna a los campos las propiedades de las rutas
     * Proporciona el elemento puntos al método para que muestre los puntos
     */
    fun mostrarRuta() {
        val element = rs?.getObject () as Element

        qNom.text = element.getElementsByTagName("nom").item(0).firstChild.nodeValue
        qDesn.text = element.getElementsByTagName("desnivell").item(0).firstChild.nodeValue
        qDesnAcum.text = element.getElementsByTagName("desnivellAcumulat").item(0).firstChild.nodeValue

        val elementPunts = element.getElementsByTagName("punts").item(0) as Element
        plenarTaula(elementPunts)
    }

    fun controlBotons() {
        primer.isEnabled = !rs!!.isFirst
        anterior.isEnabled = !rs!!.isFirst
        seguent.isEnabled = !rs!!.isLast
        ultim.isEnabled = !rs!!.isLast
    }
}

fun main(args: Array<String>) {
    EventQueue.invokeLater {
        T9Ex7_Rutes_XML_eXist().isVisible = true
    }
}

