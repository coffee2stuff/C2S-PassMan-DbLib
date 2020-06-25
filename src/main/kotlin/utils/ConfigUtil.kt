package utils

import com.fasterxml.jackson.databind.ObjectMapper

private const val CONFIG_FILE = "config.json"
private val configResource = object : Any() {}.javaClass.classLoader.getResourceAsStream(CONFIG_FILE)

fun retrieveConfigFile(): Map<*, *> = ObjectMapper().readValue(configResource, Map::class.java)