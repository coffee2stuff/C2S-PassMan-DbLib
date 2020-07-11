package di

import com.mongodb.client.MongoClients
import com.mongodb.client.MongoDatabase
import org.koin.dsl.module
import providers.MongoDbProvider
import repos.IMongoRepo
import repos.MongoRepo
import utils.retrieveConfigFile

private val configFile = retrieveConfigFile()
private val dbInstance: MongoDatabase = MongoClients
.create(configFile["mongo_connection_string"] as String)
.getDatabase(configFile["mongo_db_name"] as String)

val mongoModule = module {
    single { MongoDbProvider(database = dbInstance) }
    single<IMongoRepo> { MongoRepo(provider = get()) }
}