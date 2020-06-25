package utils

import com.fasterxml.jackson.databind.ObjectMapper
import utils.extensions.typeReference

private const val CONFIG_FILE = "config.json"
private val configResource = object : Any() {}.javaClass.classLoader.getResourceAsStream(CONFIG_FILE)

fun retrieveConfigFile(): Map<String, Any> = ObjectMapper().readValue(configResource, typeReference)