import kotlinx.coroutines.runBlocking
import models.LoginModel
import models.NoteModel
import org.apache.log4j.BasicConfigurator
import repos.MongoDbRepository

fun main() {
    BasicConfigurator.configure();
    println("C2S PassMan DbLib")

    val accessToken = "12c95a4e-b6f1-11ea-b1c0-54ee75f47269"

    val repository = MongoDbRepository()
    runBlocking {
        println(
            repository.createSingleDocument(
                NoteModel(
                    "2c8dfb74-b6f1-11ea-817f-54ee75f47269",
                    accessToken,
                    "My super secret note contents that I am not gonna tell anyone about because fluff you!"
                )
            )
        )
        println(
            repository.createSingleDocument(
                NoteModel(
                    "49689d94-b6f1-11ea-b7ab-54ee75f47269",
                    accessToken,
                    "My second note that I'm storing in here, because I love storing notes, hehe"
                )
            )
        )
        println(
            repository.createSingleDocument(
                LoginModel(
                    "6da7c978-b6f1-11ea-a02d-54ee75f47269",
                    accessToken,
                    "Google",
                    "peteralex.bizjak@gmail.com",
                    "couldYouNotM8",
                    "accounts.google.com"
                )
            )
        )
        println(
            repository.createSingleDocument(
                LoginModel(
                    "d1426222-b6f1-11ea-bce3-54ee75f47269",
                    accessToken,
                    "Outlook",
                    "peteralex.developer@gmail.com",
                    "linuxIsB3tt3r",
                    "outlook.live.com"
                )
            )
        )
        println(repository.readMultipleDocuments<LoginModel>(accessToken))
        println(
            repository.updateSingleDocument(
                "49689d94-b6f1-11ea-b7ab-54ee75f47269", NoteModel(
                    "49689d94-b6f1-11ea-b7ab-54ee75f47269",
                    accessToken,
                    "I think I should maybe update this note, because why not!"
                )
            )
        )
        println(repository.deleteSingleDocument<NoteModel>( "d1426222-b6f1-11ea-bce3-54ee75f47269"))
    }
}