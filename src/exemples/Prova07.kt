package exemples

import net.xqj.exist.ExistXQDataSource
import java.io.File
import java.io.IOException

fun main() {
    val f = File("notes.xml")
    val s = ExistXQDataSource()

    s.setProperty("serverName", "localhost")

    val conn = s.getConnection()
    val sent = "for \$classe in /classe " +
            "return <notes><modul nom=\"{\$classe/assignatura/text()}\">" +
            "{for \$alumne in \$classe//alumne " +
            "order by \$alumne/cognoms " +
            "return <alumne nota=\"{\$alumne/nota/text()}\">" +
            "{concat(\$alumne/nom/text(), \" \", \$alumne/cognoms)}" +
            "</alumne>}</modul></notes> "

    val cons = conn.prepareExpression (sent)

    val rs = cons.executeQuery ()
    var text ="<?xml version='1.0' ?>\n"
    while (rs.next()) {
        val linia = rs.getItemAsString (null)
        println(linia)
        text += linia
    }
    f.writeText(text)
    conn.close()
}

