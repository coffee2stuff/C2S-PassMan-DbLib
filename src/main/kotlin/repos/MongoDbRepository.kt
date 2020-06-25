package repos

import com.mongodb.client.result.InsertOneResult
import com.mongodb.reactivestreams.client.MongoClient
import com.mongodb.reactivestreams.client.MongoClients
import com.mongodb.reactivestreams.client.MongoDatabase
import kotlinx.coroutines.reactive.awaitFirst
import models.Model
import org.slf4j.LoggerFactory
import utils.extensions.convertToBsonDocument
import utils.retrieveConfigFile

class MongoDbRepository {

    private val mongoClient: MongoClient
    private val mongoDatabase: MongoDatabase

    private val typeToCollectionMap = mapOf(
        0 to "cards",
        1 to "logins",
        2 to "notes",
        3 to "users"
    )

    init {
        val configFile = retrieveConfigFile()
        mongoClient = MongoClients.create(configFile["mongo_connection_string"])
        mongoDatabase = mongoClient.getDatabase(configFile["mongo_db_name"])
        configureLogging()
    }

    @Throws(Throwable::class)
    suspend fun <T : Model> createSingleDocument(document: T): InsertOneResult? =
        mongoDatabase.getCollection(typeToCollectionMap[document.determinModelType()])
            .insertOne(document.convertToBsonDocument())
            .awaitFirst()

    private fun configureLogging() {
        val logger = LoggerFactory.getLogger(MongoDbRepository::class.java)
        logger.info("MongoDBRepository instantiated")
    }
}