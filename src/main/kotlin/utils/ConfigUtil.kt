package utils

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper

private const val CONFIG_FILE = "config.json"
private val configResource = object : Any() {}.javaClass.classLoader.getResourceAsStream(CONFIG_FILE)
private val typeReference = object : TypeReference<HashMap<String, Any>>() {}

fun retrieveConfigFile(): Map<String, String> = ObjectMapper().readValue(configResource, typeReference)