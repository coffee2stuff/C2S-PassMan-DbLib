import models.LoginModel
import models.NoteModel
import models.UserModel
import org.koin.core.KoinComponent
import org.koin.core.inject
import repos.IMongoRepo

class MongoService : KoinComponent {
    private val repository by inject<IMongoRepo>()

    fun createLogin(login: LoginModel): Boolean {
        return repository.createLogin(login)
    }

    fun fetchLogin(id: String, token: String): LoginModel {
        return repository.readLogin(id, token)
    }

    fun fetchAllLogins(token: String): List<LoginModel> {
        return repository.readLogins(token)
    }

    fun updateLogin(login: LoginModel): Boolean {
        return repository.updateLogin(login.id, login)
    }

    fun deleteLoginById(id: String): Boolean {
        return repository.deleteLogin(id)
    }

    fun createNote(note: NoteModel): Boolean {
        return repository.createNote(note)
    }

    fun fetchNote(id: String, token: String): NoteModel {
        return repository.readNote(id, token)
    }

    fun fetchAllNotes(token: String): List<NoteModel> {
        return repository.readNotes(token)
    }

    fun updateNote(note: NoteModel): Boolean {
        return repository.updateNote(note.id, note)
    }

    fun deleteNoteById(id: String): Boolean {
        return repository.deleteNote(id)
    }

    fun createUser(user: UserModel): Boolean {
        return repository.createUser(user)
    }

    fun fetchUser(queryById: Boolean = false, vararg queryParams: String): UserModel {
        return if (queryById) {
            repository.readUserById(queryParams[0])
        } else {
            repository.readUser(queryParams[0], queryParams[1])
        }
    }

    fun updateUser(user: UserModel): Boolean {
        return repository.updateUser(user.id, user)
    }

    fun deleteUserById(id: String): Boolean {
        return repository.deleteUser(id)
    }
}
