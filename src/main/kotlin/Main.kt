import di.mongoModule
import models.LoginModel
import org.apache.log4j.BasicConfigurator
import org.koin.core.context.startKoin
import utils.helpers.generateNumericUUID

private val service = MongoService()

fun main() {
    BasicConfigurator.configure()
    println("C2S PassMan DbLib")
    startKoin {
        modules(mongoModule)
    }

    val uuid = generateNumericUUID()
    val accessToken = "e2bd12e6-c361-11ea-b4df-54ee75f47269"

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
}