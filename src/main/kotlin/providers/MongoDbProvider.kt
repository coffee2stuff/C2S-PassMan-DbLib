package providers

import com.mongodb.client.MongoDatabase
import com.mongodb.client.model.Filters.and
import com.mongodb.client.model.Filters.eq
import com.mongodb.client.result.UpdateResult
import models.BaseModel
import models.LoginModel
import models.NoteModel
import models.UserModel
import org.slf4j.LoggerFactory
import utils.extensions.convertToBsonDocument
import utils.extensions.convertToDataClass

/**
 * Data provider for the C2S PassMan DbLib library.
 *
 * In this database architecture, the separation of concern is achieved by segmentation into three layers.
 * The first layer (this one) is a data provider, which handles generic, model-agnostic, access to CRUD
 * operations on the database. Second one is a repository which acts as a wrapper around the data provider.
 * Lastly, there's the service, which injects the repository instance. Its methods are what a library user
 * should call.
 *
 * Note on inline functions and reified type parameters: in majority of the provider methods, we need to
 * access a type passed as a parameter. One method would be walking the three using reflection to distinguish
 * between models. By marking the parameter as {@code reified}, we've allowed it being access inside the function,
 * which is inlined already, so no reflection is needed.
 *
 * Due to usage of MongoDB Sync Java driver, some compilation warnings had to be suppressed.
 *
 * @property database is an instance of the MongoDatabase
 * @constructor creates the provider instance with the MongoDatabase instance injected as a parameter
 */
@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS", "RECEIVER_NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class MongoDbProvider(val database: MongoDatabase) {
    /**
     * Public field (due to use of inline methods) binding integer model type to the string value
     * representing the collection to which such model belongs.
     */
    val typeToCollection = mapOf(0 to "logins", 1 to "notes", 2 to "users")

    /**
     * In the init{} block, we need to configure logging with SLF4J which is a mandatory component of the
     * MongoDB Sync Java driver...
     */
    init {
        configureLogging()
    }

    /**
     * Create a [document] of a generic type T.
     *
     * @param T invariant type parameter with the upper bound BaseModel. The document type it represents has
     * to be one of the supported model types that extend the BaseModel class for information related to
     * their type, which is bound to a specific collection, as described by {@code typeToCollection}.
     * @return boolean value representing whether or not the database operation was acknowledge from the
     * side of the MongoDB.
     */
    inline fun <reified T : BaseModel> createDocument(document: T): Boolean =
        database
            .getCollection(typeToCollection[document.modelType()])
            .insertOne(document.convertToBsonDocument())
            .wasAcknowledged()

    /**
     * Read a single document of a generic type T, converted from Bson document to a data class represented
     * by the generic type. In this particular instance, we query the database by the document [id], which
     * should not be mixed with the MongoDB's ObjectId field!
     *
     * @param T invariant type parameter with the upper bound BaseModel. The document type it represents has
     * to be one of the supported model types that extend the BaseModel class for information related to
     * their type, which is bound to a specific collection, as described by {@code typeToCollection}.
     * @return generic type T object representing one of the supported data classes.
     */
    inline fun <reified T : BaseModel> readSingleDocumentById(id: String): T {
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

    /**
     * Read a single document of a generic type T, converted from Bson document to a data class represented
     * by the generic type. In this particular instance, we query the database by the document [id] and [token].
     * Here, [token] refers to user's access token.
     *
     * @param T invariant type parameter with the upper bound BaseModel. The document type it represents has
     * to be one of the supported model types that extend the BaseModel class for information related to
     * their type, which is bound to a specific collection, as described by {@code typeToCollection}.
     * @return generic type T object representing one of the supported data classes.
     */
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

    /**
     * Method exclusively reserved for reading UserModel from provided [email] and [password].
     */
    fun readUserByEmailAndPassword(email: String, password: String): UserModel {
        return database
            .getCollection("users")
            .find(and(eq("email", email), eq("password", password)))
            .first()
            .convertToDataClass<UserModel>()
    }

    /**
     * Read multiple documents of a generic type T, converted from Bson document to list of data class represented
     * by the generic type. In this particular instance, we query the database by the document [accessToken].
     * Here, [accessToken] refers to user's access token.
     *
     * @param T invariant type parameter with the upper bound BaseModel. The document type it represents has
     * to be one of the supported model types that extend the BaseModel class for information related to
     * their type, which is bound to a specific collection, as described by {@code typeToCollection}.
     * @return list of generic type T objects representing one of the supported data classes.
     */
    inline fun <reified T : BaseModel> readMultipleDocuments(accessToken: String): List<T>? {
        val type = when {
            T::class == LoginModel::class -> 0
            T::class == NoteModel::class -> 1
            T::class == UserModel::class -> 2
            else -> -1
        }
        return database
            .getCollection(typeToCollection[type])
            .find(eq("access_token", accessToken))
            .toList()
            .map { it.convertToDataClass<T>() }
    }

    /**
     * Update a [document] of a generic type T with the provided [id] of the said document. The [id] should
     * not be mixed with the MongoDB's ObjectId field!
     *
     * @param T invariant type parameter with the upper bound BaseModel. The document type it represents has
     * to be one of the supported model types that extend the BaseModel class for information related to
     * their type, which is bound to a specific collection, as described by {@code typeToCollection}.
     * @return boolean value representing whether or not the database operation was acknowledge from the
     * side of the MongoDB.
     */
    inline fun <reified T : BaseModel> updateSingleDocument(id: String, document: T): Boolean = database
        .getCollection(typeToCollection[document.modelType()])
        .replaceOne(eq("id", id), document.convertToBsonDocument())
        .wasAcknowledged()

    /**
     * Delete a document of a generic type T with the provided [id] of the said document. The [id] should
     * not be mixed with the MongoDB's ObjectId field!
     *
     * @param T invariant type parameter with the upper bound BaseModel. The document type it represents has
     * to be one of the supported model types that extend the BaseModel class for information related to
     * their type, which is bound to a specific collection, as described by {@code typeToCollection}.
     * @return boolean value representing whether or not the database operation was acknowledge from the
     * side of the MongoDB.
     */
    inline fun <reified T : BaseModel> deleteSingleDocument(id: String): Boolean {
        val type = when {
            T::class == LoginModel::class -> 0
            T::class == NoteModel::class -> 1
            T::class == UserModel::class -> 2
            else -> -1
        }
        return database
            .getCollection(typeToCollection[type])
            .deleteOne(eq("id", id))
            .wasAcknowledged()
    }

    /**
     * Logger configuration with the SLF4J logging library for this particular class.
     */
    private fun configureLogging() {
        val logger = LoggerFactory.getLogger(MongoDbProvider::class.java)
        logger.info("MongoDbProvider instantiated")
    }
}