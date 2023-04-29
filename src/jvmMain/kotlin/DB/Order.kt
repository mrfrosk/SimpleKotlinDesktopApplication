package DB

import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.kotlin.datetime.date

object Order: IntIdTable() {
    val customer = reference("customerId", Customer.id)
    val date = date("date")
    val price = integer("price")
    val isExecuted = bool("isExecuted")
}