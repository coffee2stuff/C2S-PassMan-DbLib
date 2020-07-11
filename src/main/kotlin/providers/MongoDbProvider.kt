package providers

import com.mongodb.client.MongoDatabase
import com.mongodb.client.model.Filters.and
import com.mongodb.client.model.Filters.eq
import models.BaseModel
import models.LoginModel
import models.NoteModel
import models.UserModel
import org.slf4j.LoggerFactory
import utils.extensions.convertToBsonDocument
import utils.extensions.convertToDataClass

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS", "RECEIVER_NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
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

    inline fun <reified T: BaseModel> readSingleDocumentById(id: String): T {
        val type = when {
            T::class == LoginModel::class -> 0
            T::class == NoteModel::class -> 1
            T::class == UserModel::class -> 2
            else -> -1
        }
        return database
            .getCollection(typeToCollection[type])
            .find(eq("id", id))
            .first()
            .convertToDataClass()
    }

    inline fun <reified T : BaseModel> readSingleDocumentByIdAndToken(id: String, token: String): T {
        val type = when {
            T::class == LoginModel::class -> 0
            T::class == NoteModel::class -> 1
            T::class == UserModel::class -> 2
            else -> -1
        }
        return database
            .getCollection(typeToCollection[type])
            .find(and(eq("id", id), eq("access_token", token)))
            .first()
            .convertToDataClass()
    }

    fun readUserByEmailAndPassword(email: String, password: String): UserModel {
        return database
            .getCollection("users")
            .find(and(eq("email", email), eq("password", password)))
            .first()
            .convertToDataClass<UserModel>()
    }

    private fun configureLogging() {
        val logger = LoggerFactory.getLogger(MongoDbProvider::class.java)
        logger.info("MongoDbProvider instantiated")
    }
}