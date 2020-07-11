package repos

import models.LoginModel
import providers.MongoDbProvider

class MongoRepo(private val provider: MongoDbProvider) : IMongoRepo {
    override fun createLogin(login: LoginModel): Boolean = provider.createDocument(login)
}