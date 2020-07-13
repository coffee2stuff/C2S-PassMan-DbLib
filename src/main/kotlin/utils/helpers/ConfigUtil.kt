package utils.helpers

import com.fasterxml.jackson.databind.ObjectMapper
import utils.extensions.typeReference

/**
 * Name of the MongoDb configuration file referenced in the README.md. This file holds database connection string and
 * the database name.
 */
private const val CONFIG_FILE = "config.json"

/**
 * InputStream object which holds the contents of the configuration file. In order to bypass the issue with using top-level
 * members and classLoader calls, this operation takes a little bit of a Kotlin's black magic and spices it up...
 */
private val configResource = object : Any() {}.javaClass.classLoader.getResourceAsStream(CONFIG_FILE)

/**
 * Retrieves the configuration file from the InputStream and deserializes it into a Map<String, Any> object. Since we're
 * not operating with Kotlin's data classes, there is no need of using {@code mapper} from {@code ConversionExtensions.kt}
 * file.
 *
 * @return map representation of the configuration file with all of its contents.
 */
fun retrieveConfigFile(): Map<String, Any> = ObjectMapper().readValue(configResource, typeReference)