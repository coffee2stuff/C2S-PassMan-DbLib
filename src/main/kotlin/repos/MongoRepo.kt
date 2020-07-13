package repos

import models.LoginModel
import models.NoteModel
import models.UserModel
import providers.MongoDbProvider

class MongoRepo(private val provider: MongoDbProvider) : IMongoRepo {
    override fun createLogin(login: LoginModel): Boolean = provider.createDocument<LoginModel>(login)
    override fun readLogin(id: String, token: String): LoginModel = provider.readSingleDocumentByIdAndToken<LoginModel>(id, token)
    override fun readLogins(token: String): List<LoginModel> = provider.readMultipleDocuments<LoginModel>(token) ?: emptyList()
    override fun updateLogin(id: String, login: LoginModel) = provider.updateSingleDocument<LoginModel>(id, login)
    override fun deleteLogin(id: String) = provider.deleteSingleDocument<LoginModel>(id)

    override fun createNote(note: NoteModel): Boolean = provider.createDocument<NoteModel>(note)
    override fun readNote(id: String, token: String): NoteModel = provider.readSingleDocumentByIdAndToken<NoteModel>(id, token)
    override fun readNotes(token: String): List<NoteModel> = provider.readMultipleDocuments<NoteModel>(token) ?: emptyList()
    override fun updateNote(id: String, note: NoteModel) = provider.updateSingleDocument<NoteModel>(id, note)
    override fun deleteNote(id: String) = provider.deleteSingleDocument<NoteModel>(id)

    override fun createUser(user: UserModel): Boolean = provider.createDocument<UserModel>(user)
    override fun readUserById(id: String): UserModel = provider.readSingleDocumentById<UserModel>(id)
    override fun readUser(email: String, password: String): UserModel = provider.readUserByEmailAndPassword(email, password)
    override fun updateUser(id: String, user: UserModel) = provider.updateSingleDocument<UserModel>(id, user)
    override fun deleteUser(id: String) = provider.deleteSingleDocument<UserModel>(id)
}