package utils.extensions

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.fasterxml.jackson.module.kotlin.readValue
import org.bson.Document

/**
 * ObjectMapper instance with the registered KotlinModule. This way, we enable Jackson to better serialize
 * and deserialize Kotlin's data classes.
 */
val mapper: ObjectMapper = ObjectMapper().registerModule(KotlinModule())

/**
 * TypeReference instance describing the Map object where key is a String and value an Any type.
 */
val typeReference = object : TypeReference<Map<String, Any>>() {}

/**
 * Convert a data class to map.
 *
 * @param T generic type parameter representing the input data class.
 * @return Map<String, Any> object representing said data class, using the {@code mapper} and {@code typeReference}
 * declared above.
 */
inline fun <reified T> T.toMap(): Map<String, Any> = mapper.convertValue(this, typeReference)

/**
 * Convert a data class to Bson Document object. Because the Document is a subclass of a Map, the operation works by
 * converting the data class to a map first, and then creating an instance of Document.
 *
 * @param T generic type parameter representing the input data class.
 * @return Bson Document object generated from the said data class.
 */
inline fun <reified T> T.convertToBsonDocument(): Document = Document(this.toMap())

/**
 * Convert a Map<String, Any> object to the data class. This is an inverse operation to {@code T.toMap()}.
 *
 * @param T generic type parameter representing the output data class.
 * @return data class which we deserialize from the input map.
 */
inline fun <reified T> Map<String, Any>.convertToDataClass(): T = mapper.readValue(mapper.writeValueAsString((this)))