package exemples

import net.xqj.exist.ExistXQDataSource
import javax.xml.xquery.XQResultItem
import javax.xml.xquery.XQConstants

fun main() {
    val s = ExistXQDataSource()

    s.setProperty("serverName", "89.36.214.106")

    val conn = s.getConnection()
    //val sent = "for \$alumne in doc(\"/db/Tema9/classe.xml\")//alumne  return xs:int(\$alumne/nota/text())"
    val sent = "for \$alumne in //alumne  return xs:int(\$alumne/nota/text())"
    val rs = conn.createExpression().executeQuery(sent)

    while (rs.next())
        println(rs.getInt())

    conn.close()
}

