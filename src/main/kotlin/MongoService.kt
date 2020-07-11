import models.LoginModel
import org.koin.core.KoinComponent
import org.koin.core.inject
import repos.IMongoRepo

class MongoService : KoinComponent {
    private val repository by inject<IMongoRepo>()

    fun createLogin(login: LoginModel): Status? {
        val success = repository.createLogin(login)
        if (success) {
            return Status("Performed DB operation", "Stored login details")
        }
        return null
    }
}

data class Status(
    val title: String,
    val description: String?
)