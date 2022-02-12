package exemples

import net.xqj.exist.ExistXQDataSource
import javax.xml.xquery.XQResultItem
import javax.xml.xquery.XQConstants

fun main() {
    val s = ExistXQDataSource()

    s.setProperty("serverName", "localhost")

    val conn = s.getConnection()
    val sent = "for \$ruta in doc(\"/db/Tema9/Rutes.xml\")//ruta return xs:string(\$ruta)"
    val rs = conn.createExpression().executeQuery(sent)

    while (rs.next())
        println(rs.getItemAsString(null))

    conn.close()
}