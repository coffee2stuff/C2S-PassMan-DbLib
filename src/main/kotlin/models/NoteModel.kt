package models

import com.fasterxml.jackson.annotation.JsonProperty

data class NoteModel(
    @JsonProperty("id")
    val id: String,

    @JsonProperty("access_token")
    val accessToken: String,

    @JsonProperty("contents")
    val noteContents: String
) : Model() {
    override fun determinModelType(): Int = 2
}