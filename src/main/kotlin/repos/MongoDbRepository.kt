package repos

import com.mongodb.client.MongoClients
import com.mongodb.client.MongoDatabase
import com.mongodb.client.model.Filters.and
import com.mongodb.client.model.Filters.eq
import com.mongodb.client.result.DeleteResult
import com.mongodb.client.result.InsertOneResult
import com.mongodb.client.result.UpdateResult
import models.*
import org.slf4j.LoggerFactory
import utils.extensions.convertToBsonDocument
import utils.extensions.convertToDataClass
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

    @Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
    @Throws(Throwable::class)
    inline fun <reified T : BaseModel> createSingleDocument(document: T): InsertOneResult? {
        val typeToCollection = mapOf(0 to "cards", 1 to "logins", 2 to "notes", 3 to "users")
        return db.getCollection(typeToCollection[document.modelType()])
            .insertOne(document.convertToBsonDocument())
    }

    @Suppress(
        "RECEIVER_NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS",
        "NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS"
    )
    inline fun <reified T : BaseModel> readSingleDocument(
        id: String,
        accessToken: String,
        email: String = "",
        password: String = ""
    ): T? {
        val type = when {
            T::class == CardModel::class -> 0
            T::class == LoginModel::class -> 1
            T::class == NoteModel::class -> 2
            T::class == UserModel::class -> 3
            else -> -1
        }
        val typeToCollection = mapOf(0 to "cards", 1 to "logins", 2 to "notes", 3 to "users")
        return db.getCollection(typeToCollection[type])
            .find(
                if (email.isNotEmpty() && password.isNotEmpty()) and(
                    eq("email", email),
                    eq("password", password)
                ) else and(eq("id", id), eq("access_token", accessToken))
            ).first()
            .convertToDataClass()
    }

    @Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
    inline fun <reified T : BaseModel> readMultipleDocuments(accessToken: String): List<T>? {
        val type = when {
            T::class == CardModel::class -> 0
            T::class == LoginModel::class -> 1
            T::class == NoteModel::class -> 2
            else -> -1
        }
        val typeToCollection = mapOf(0 to "cards", 1 to "logins", 2 to "notes")
        return db.getCollection(typeToCollection[type])
            .find(eq("access_token", accessToken))
            .toList()
            .map { it.convertToDataClass<T>() }
    }

    @Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
    inline fun <reified T : BaseModel> updateSingleDocument(id: String, document: T): UpdateResult? {
        val typeToCollection = mapOf(0 to "cards", 1 to "logins", 2 to "notes", 3 to "users")
        return db.getCollection(typeToCollection[document.modelType()])
            .replaceOne(eq("id", id), document.convertToBsonDocument())
    }

    @Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
    inline fun <reified T : BaseModel> deleteSingleDocument(id: String): DeleteResult? {
        val type = when {
            T::class == CardModel::class -> 0
            T::class == LoginModel::class -> 1
            T::class == NoteModel::class -> 2
            T::class == UserModel::class -> 3
            else -> -1
        }
        val typeToCollection = mapOf(0 to "cards", 1 to "logins", 2 to "notes", 3 to "users")
        return db.getCollection(typeToCollection[type])
            .deleteOne(eq("id", id))
    }

    private fun configureLogging() {
        val logger = LoggerFactory.getLogger(MongoDbRepository::class.java)
        logger.info("MongoDBRepository instantiated")
    }
}
