import models.*
import org.junit.Assert.assertEquals
import org.junit.Test

class InstanceRecognitionUnit {

    @Test
    fun recognizeCorrectType() {
        val type = recognizeClassInstance<NoteModel>()
        assertEquals(2, type)
    }

    private inline fun <reified T : BaseModel> recognizeClassInstance(): Int {
        return when {
            T::class == CardModel::class -> 0
            T::class == LoginModel::class -> 1
            T::class == NoteModel::class -> 2
            T::class == UserModel::class -> 3
            else -> -1
        }
    }
}