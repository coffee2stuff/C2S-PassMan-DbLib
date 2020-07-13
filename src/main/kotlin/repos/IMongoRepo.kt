package repos

import models.LoginModel
import models.NoteModel
import models.UserModel

interface IMongoRepo {
    fun createLogin(login: LoginModel): Boolean
    fun readLogin(id: String, token: String): LoginModel
    fun readLogins(token: String): List<LoginModel>
    fun updateLogin(id: String, login: LoginModel): Boolean
    fun deleteLogin(id: String): Boolean

    fun createNote(note: NoteModel): Boolean
    fun readNote(id: String, token: String): NoteModel
    fun readNotes(token: String): List<NoteModel>
    fun updateNote(id: String, note: NoteModel): Boolean
    fun deleteNote(id: String): Boolean

    fun createUser(user: UserModel): Boolean
    fun readUserById(id: String): UserModel
    fun readUser(email: String, password: String): UserModel
    fun updateUser(id: String, user: UserModel): Boolean
    fun deleteUser(id: String): Boolean
}