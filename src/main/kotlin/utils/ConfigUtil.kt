package utils

import utils.extensions.objectMapper
import utils.extensions.typeReference

private const val CONFIG_FILE = "config.json"
private val configResource = object : Any() {}.javaClass.classLoader.getResourceAsStream(CONFIG_FILE)

fun retrieveConfigFile(): Map<String, Any> = objectMapper.readValue(configResource, typeReference)