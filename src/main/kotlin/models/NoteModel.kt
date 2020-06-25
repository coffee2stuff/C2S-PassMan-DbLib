package models

import com.fasterxml.jackson.annotation.JsonProperty

data class NoteModel(
    @JsonProperty("id")
    val id: String,

    @JsonProperty("access_token")
    val accessToken: String,

    @JsonProperty("contents")
    val noteContents: String
) : BaseModel() {
    override fun modelType(): Int = 2
}