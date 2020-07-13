import models.LoginModel
import models.NoteModel
import models.UserModel
import org.koin.core.KoinComponent
import org.koin.core.inject
import repos.IMongoRepo

/**
 * Service for the C2S PassMan DbLib library.
 *
 * In this database architecture, the separation of concern is achieved by segmentation into three layers.
 * The first layer is a data provider, which handles generic, model-agnostic, access to CRUD
 * operations on the database. Second one is a repository which acts as a wrapper around the data provider.
 * Lastly, there's the service (this one), which injects the repository instance. Its methods are what a library user
 * should call.
 */
class MongoService : KoinComponent {
    /**
     * Lazily injected instance of the repository (the declaration, not implementation).
     */
    private val repository by inject<IMongoRepo>()

    /**
     * Create a new [login].
     *
     * @return boolean true/false corresponding to the success/failure of the database operation.
     */
    fun createLogin(login: LoginModel): Boolean {
        return repository.createLogin(login)
    }

    /**
     * Fetch a single login model from [id] and [token].
     *
     * @return login model.
     */
    fun fetchLogin(id: String, token: String): LoginModel {
        return repository.readLogin(id, token)
    }

    /**
     * Fetch multiple login models from [token].
     *
     * @return list of login models.
     */
    fun fetchAllLogins(token: String): List<LoginModel> {
        return repository.readLogins(token)
    }

    /**
     * Update a [login].
     *
     * @return boolean true/false corresponding to the success/failure of the database operation.
     */
    fun updateLogin(login: LoginModel): Boolean {
        return repository.updateLogin(login.id, login)
    }

    /**
     * Delete a login by [id].
     *
     * @return boolean true/false corresponding to the success/failure of the database operation.
     */
    fun deleteLoginById(id: String): Boolean {
        return repository.deleteLogin(id)
    }

    /**
     * Create a new [note].
     *
     * @return boolean true/false corresponding to the success/failure of the database operation.
     */
    fun createNote(note: NoteModel): Boolean {
        return repository.createNote(note)
    }

    /**
     * Fetch a single note model from [id] and [token].
     *
     * @return note model.
     */
    fun fetchNote(id: String, token: String): NoteModel {
        return repository.readNote(id, token)
    }

    /**
     * Fetch multiple note models from [token].
     *
     * @return list of note models.
     */
    fun fetchAllNotes(token: String): List<NoteModel> {
        return repository.readNotes(token)
    }

    /**
     * Update a [note].
     *
     * @return boolean true/false corresponding to the success/failure of the database operation.
     */
    fun updateNote(note: NoteModel): Boolean {
        return repository.updateNote(note.id, note)
    }

    /**
     * Delete a note by [id].
     *
     * @return boolean true/false corresponding to the success/failure of the database operation.
     */
    fun deleteNoteById(id: String): Boolean {
        return repository.deleteNote(id)
    }

    /**
     * Create a new [user].
     *
     * @return boolean true/false corresponding to the success/failure of the database operation.
     */
    fun createUser(user: UserModel): Boolean {
        return repository.createUser(user)
    }

    /**
     * Fetch a single user model from provided [queryParams]. If you want to query the database
     * for a user by its ID, then set the [queryById] to true and only pass the user's unique
     * ID as a [queryParams]: {@code service.fetchUser(true, "user-id")}. Otherwise (which is
     * a preferred and default method), set the [queryById] to false and provide email/password
     * combination as [queryParams]: {@code service.fetchUser(false, "email-here", "password-here")}.
     *
     * @return user model.
     */
    fun fetchUser(queryById: Boolean = false, vararg queryParams: String): UserModel {
        return if (queryById) {
            repository.readUserById(queryParams[0])
        } else {
            repository.readUser(queryParams[0], queryParams[1])
        }
    }

    /**
     * Update a [user].
     *
     * @return boolean true/false corresponding to the success/failure of the database operation.
     */
    fun updateUser(user: UserModel): Boolean {
        return repository.updateUser(user.id, user)
    }

    /**
     * Delete a user by [id].
     *
     * @return boolean true/false corresponding to the success/failure of the database operation.
     */
    fun deleteUserById(id: String): Boolean {
        return repository.deleteUser(id)
    }
}
