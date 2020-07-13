import di.mongoModule
import models.LoginModel
import models.NoteModel
import models.UserModel
import org.apache.log4j.BasicConfigurator
import org.koin.core.context.startKoin
import utils.helpers.generateNumericUUID

private val service = MongoService()

private const val accessToken = "e2bd12e6-c361-11ea-b4df-54ee75f47269"

fun main() {
    BasicConfigurator.configure()
    println("C2S PassMan DbLib")
    startKoin {
        modules(mongoModule)
    }

    // TODO: remove after development!
    userOperations()
    loginOperations()
    noteOperations()
}

private fun userOperations() {
    val uuid = generateNumericUUID()
    val createUserResult = service.createUser(
        UserModel(
            uuid,
            accessToken,
            "John Doe",
            "john.doe@gmail.com",
            "myNameIsJohn"
        )
    )
    println("Create user: $createUserResult")

    val readUserResultById = service.fetchUser(true, uuid)
    val readUserByEmailPassword = service.fetchUser(false, "john.doe@gmail.com", "myNameIsJohn")
    println("The user fetches by ID ($readUserResultById) and by email/password ($readUserByEmailPassword) should match")
}

private fun loginOperations() {
    val uuid = generateNumericUUID()

    val createLoginResult = service.createLogin(
        LoginModel(
            uuid,
            accessToken,
            "Gmail",
            "john.doe@gmail.com",
            "testPasswordYolo",
            "accounts.google.com"
        )
    )
    println("Create login: $createLoginResult")

    val readLoginResult = service.fetchLogin(uuid, accessToken)
    println("Read login: $readLoginResult")

    val readAllLoginsResult = service.fetchAllLogins(accessToken)
    println("Number of all logins in the database: ${readAllLoginsResult.size}")
}


private fun noteOperations() {
    for (i in 0..3) {
        val createNoteResult = service.createNote(NoteModel(generateNumericUUID(), accessToken, "Note #$i is here!"))
        println("Create note: $createNoteResult")
    }

    val readAllNotesResult = service.fetchAllNotes(accessToken)
    println("Number of all notes in the database: ${readAllNotesResult.size}")

}