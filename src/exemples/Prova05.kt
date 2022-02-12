package exemples

import net.xqj.exist.ExistXQDataSource
import javax.xml.xquery.XQResultItem
import javax.xml.xquery.XQConstants

fun main() {
    val s = ExistXQDataSource()

    s.setProperty("serverName", "89.36.214.106")

    val conn = s.getConnection()
    val sent = "//alumne/cognoms/xs:string(text())"
    val rs = conn.createExpression().executeQuery(sent)

    while (rs.next())
        println(rs.getItemAsString(null))

    conn.close()
}

