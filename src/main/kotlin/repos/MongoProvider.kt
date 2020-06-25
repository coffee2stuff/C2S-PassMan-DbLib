package repos

import com.mongodb.reactivestreams.client.MongoClient
import com.mongodb.reactivestreams.client.MongoClients
import com.mongodb.reactivestreams.client.MongoDatabase
import utils.retrieveConfigFile

class MongoProvider {
    private val mongoClient: MongoClient
    private val mongoDatabase: MongoDatabase

    init {
        val configFile = retrieveConfigFile()
        mongoClient = MongoClients.create(configFile["mongo_connection_string"])
        mongoDatabase = mongoClient.getDatabase(configFile["mongo_db_name"])
    }
}