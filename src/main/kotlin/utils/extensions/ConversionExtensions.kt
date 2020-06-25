package utils.extensions

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.fasterxml.jackson.module.kotlin.readValue
import org.bson.Document

val objectMapper = ObjectMapper()
val mapper: ObjectMapper = objectMapper.registerModule(KotlinModule())
val typeReference = object : TypeReference<Map<String, Any>>() {}

inline fun <reified T> T.toMap(): Map<String, Any> = objectMapper.convertValue(this, typeReference)

inline fun <reified T> T.convertToBsonDocument(): Document = Document(this.toMap())

inline fun <reified T> Map<String, Any>.convertToDataClass(): T = mapper.readValue(objectMapper.writeValueAsString((this)))