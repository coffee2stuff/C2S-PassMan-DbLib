package models

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

/**
 * Note model
 */
@JsonIgnoreProperties(value = ["_id", "__v"])
data class NoteModel(
    /**
     * Unique ID of the model.
     */
    @JsonProperty("id")
    val id: String,

    /**
     * User access token allowing us to determine which user is the
     * owner of this model.
     */
    @JsonProperty("access_token")
    val accessToken: String,

    /**
     * Contents of the note.
     */
    @JsonProperty("contents")
    val noteContents: String
) : BaseModel() {
    /**
     * Overridden method from the BaseModel class declaring login to be type 1.
     */
    override fun modelType(): Int = 1
}