package repos

import models.LoginModel
import models.NoteModel
import models.UserModel

interface IMongoRepo {
    fun createLogin(login: LoginModel): Boolean
    fun readLogin(id: String, token: String): LoginModel
    fun readLogins(token: String): List<LoginModel>

    fun createNote(note: NoteModel): Boolean
    fun readNote(id: String, token: String): NoteModel
    fun readNotes(token: String): List<NoteModel>

    fun createUser(user: UserModel): Boolean
    fun readUserById(id: String): UserModel
    fun readUser(email: String, password: String): UserModel
}