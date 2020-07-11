package providers

import com.mongodb.client.MongoDatabase
import models.BaseModel
import org.slf4j.LoggerFactory
import utils.extensions.convertToBsonDocument

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class MongoDbProvider(val database: MongoDatabase) {
    val typeToCollection = mapOf(0 to "logins", 1 to "notes", 2 to "users")

    init {
        configureLogging()
    }

    inline fun <reified T : BaseModel> createDocument(document: T): Boolean =
        database
            .getCollection(typeToCollection[document.modelType()])
            .insertOne(document.convertToBsonDocument())
            .wasAcknowledged()

    private fun configureLogging() {
        val logger = LoggerFactory.getLogger(MongoDbProvider::class.java)
        logger.info("MongoDbProvider instantiated")
    }
}