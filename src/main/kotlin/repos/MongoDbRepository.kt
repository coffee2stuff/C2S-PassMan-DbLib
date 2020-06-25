package repos

import com.mongodb.client.model.Filters.and
import com.mongodb.client.model.Filters.eq
import com.mongodb.client.result.DeleteResult
import com.mongodb.client.result.InsertOneResult
import com.mongodb.client.result.UpdateResult
import com.mongodb.reactivestreams.client.MongoClients
import com.mongodb.reactivestreams.client.MongoDatabase
import kotlinx.coroutines.reactive.awaitFirstOrNull
import models.BaseModel
import org.slf4j.LoggerFactory
import utils.extensions.convertToBsonDocument
import utils.extensions.convertToDataClass
import utils.extensions.toMap
import utils.retrieveConfigFile

class MongoDbRepository {

    val db: MongoDatabase

    init {
        val configFile = retrieveConfigFile()
        db = MongoClients
            .create(configFile["mongo_connection_string"] as String)
            .getDatabase(configFile["mongo_db_name"] as String)
        configureLogging()
    }

    @Throws(Throwable::class)
    suspend inline fun <reified T : BaseModel> createSingleDocument(document: T): InsertOneResult? {
        val typeToCollection = mapOf(0 to "cards", 1 to "logins", 2 to "notes", 3 to "users")
        return db.getCollection(typeToCollection[document.modelType()])
            .insertOne(document.convertToBsonDocument())
            .awaitFirstOrNull()
    }

    suspend inline fun <reified T : BaseModel> readSingleDocument(
        type: Int,
        id: String,
        accessToken: String,
        email: String = "",
        password: String = ""
    ): T? {
        val typeToCollection = mapOf(0 to "cards", 1 to "logins", 2 to "notes", 3 to "users")
        return db.getCollection(typeToCollection[type])
            .find(
                if (email.isNotEmpty() && password.isNotEmpty()) and(
                    eq("email", email),
                    eq("password", password)
                ) else and(eq("id", id), eq("access_token", accessToken))
            )
            .awaitFirstOrNull()?.toMap()?.convertToDataClass()
    }

    suspend inline fun <reified T : BaseModel> readMultipleDocuments(type: Int, access_token: String): List<T>? {
        val typeToCollection = mapOf(0 to "cards", 1 to "logins", 2 to "notes")
        return db.getCollection(typeToCollection[type])
            .find(eq("access_token", access_token))
            .awaitFirstOrNull()
            ?.map { (it.toMap()).convertToDataClass<T>() }
    }

    suspend inline fun <reified T : BaseModel> updateSingleDocument(id: String, document: T): UpdateResult? {
        val typeToCollection = mapOf(0 to "cards", 1 to "logins", 2 to "notes", 3 to "users")
        return db.getCollection(typeToCollection[document.modelType()])
            .updateOne(eq("id", id), document.convertToBsonDocument())
            .awaitFirstOrNull()
    }

    suspend inline fun deleteSingleDocument(type: Int, id: String): DeleteResult? {
        val typeToCollection = mapOf(0 to "cards", 1 to "logins", 2 to "notes", 3 to "users")
        return db.getCollection(typeToCollection[type])
            .deleteOne(eq("id", id))
            .awaitFirstOrNull()
    }

    private fun configureLogging() {
        val logger = LoggerFactory.getLogger(MongoDbRepository::class.java)
        logger.info("MongoDBRepository instantiated")
    }
}