package di

import com.mongodb.client.MongoClients
import com.mongodb.client.MongoDatabase
import org.koin.dsl.module
import providers.MongoDbProvider
import repos.IMongoRepo
import repos.MongoRepo
import utils.helpers.retrieveConfigFile

/**
 * Configuration file retrieved as Map.
 */
private val configFile = retrieveConfigFile()

/**
 * Build an instance of a MongoDatabase object.
 */
private val dbInstance: MongoDatabase = MongoClients
    .create(configFile["mongo_connection_string"] as String)
    .getDatabase(configFile["mongo_db_name"] as String)

/**
 * Koin module definition. Each component is a singleton (provider and repository).
 */
val mongoModule = module {
    single { MongoDbProvider(database = dbInstance) }
    single<IMongoRepo> { MongoRepo(provider = get()) }
}