import di.mongoModule
import org.apache.log4j.BasicConfigurator
import org.koin.core.context.startKoin

fun main() {
    BasicConfigurator.configure()
    println("C2S PassMan DbLib")
    startKoin {
        modules(mongoModule)
    }
}