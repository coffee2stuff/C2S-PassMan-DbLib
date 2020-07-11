package utils.helpers

import java.lang.Long.parseLong
import java.util.*

fun generateNumericUUID(): String {
    val uuid = UUID.randomUUID().toString().replace("-", "")
    var newUUID = ""
    for (i in 0..8) {
        newUUID += uuid.random()
    }
    return parseLong(newUUID, 16).toString()
}