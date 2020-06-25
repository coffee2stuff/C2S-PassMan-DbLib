package utils.extensions

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import org.bson.Document

private val typeReference = object : TypeReference<Map<String, Any>>() {}
private fun <T> T.toMap(): Map<String, Any> = ObjectMapper().convertValue(this, typeReference)

fun Map<String, Any>.convertToBsonDocument(): Document = Document(this)

fun <T> T.convertToBsonDocument(): Document = Document(this.toMap())