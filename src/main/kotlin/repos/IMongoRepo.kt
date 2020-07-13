package repos

import models.LoginModel
import models.NoteModel
import models.UserModel

/**
 * In the typical application using Koin as a dependency injection framework, {@code single} and\
 * {@code factory} object declarations allow you to include a type in angle brackets and a lambda
 * expression, which defines the way the object will be constructed. Due to SOLID principles,
 * the indicated type is usually an interface that the object to inject has to implement. This makes
 * this object easily exchangeable in the future.
 *
 * This interface defines basic CRUD operations on supported models.
 */
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