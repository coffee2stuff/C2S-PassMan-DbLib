package repos

import models.LoginModel
import providers.MongoDbProvider

class MongoRepo(private val provider: MongoDbProvider) : IMongoRepo {
    override fun createLogin(login: LoginModel): Boolean = provider.createDocument(login)
    override fun readLogin(id: String, token: String): LoginModel = provider.readSingleDocumentByIdAndToken<LoginModel>(id, token)
}