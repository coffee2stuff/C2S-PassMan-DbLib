package repos

import com.mongodb.client.model.Filters.and
import com.mongodb.client.model.Filters.eq
import com.mongodb.client.result.DeleteResult
import com.mongodb.client.result.InsertOneResult
import com.mongodb.client.result.UpdateResult
import com.mongodb.reactivestreams.client.MongoClients
import com.mongodb.reactivestreams.client.MongoDatabase
import kotlinx.coroutines.reactive.awaitFirstOrNull
import models.*
import org.bson.Document
import org.reactivestreams.Subscriber
import org.reactivestreams.Subscription
import org.slf4j.LoggerFactory
import utils.extensions.convertToBsonDocument
import utils.extensions.convertToDataClass
import utils.extensions.toMap
import utils.retrieveConfigFile
import kotlin.reflect.KClass

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
            )
            .awaitFirstOrNull()?.toMap()?.convertToDataClass<T>()
    }

    suspend inline fun <reified T : BaseModel> readMultipleDocuments(access_token: String): List<T>? {
        val type = when {
            T::class == CardModel::class -> 0
            T::class == LoginModel::class -> 1
            T::class == NoteModel::class -> 2
            else -> -1
        }
        val typeToCollection = mapOf(0 to "cards", 1 to "logins", 2 to "notes")
        val list = mutableListOf<T>()
        val cursor = db.getCollection(typeToCollection[type])
            .find(eq("access_token", access_token))
            .awaitFirstOrNull() ?: Document("null", null)
        println("Cursor entries: $cursor")
        cursor.asIterable().forEach {
            println("Iterable item: $it")
            list.add(it.toMap().convertToDataClass())
        }
        return list
    }

    suspend inline fun <reified T : BaseModel> updateSingleDocument(id: String, document: T): UpdateResult? {
        val typeToCollection = mapOf(0 to "cards", 1 to "logins", 2 to "notes", 3 to "users")
        return db.getCollection(typeToCollection[document.modelType()])
            .updateOne(eq("id", id), document.convertToBsonDocument())
            .awaitFirstOrNull()
    }

    suspend inline fun <reified T : BaseModel> deleteSingleDocument(id: String): DeleteResult? {
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
            .awaitFirstOrNull()
    }

    private fun configureLogging() {
        val logger = LoggerFactory.getLogger(MongoDbRepository::class.java)
        logger.info("MongoDBRepository instantiated")
    }
}