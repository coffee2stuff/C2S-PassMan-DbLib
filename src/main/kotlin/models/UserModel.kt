package models

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

/**
 * User model
 */
@JsonIgnoreProperties(value = ["_id", "__v"])
data class UserModel(
    /**
     * Unique ID of the model.
     */
    @JsonProperty("id")
    val id: String,

    /**
     * Generated access token that the user will require in subsequent requests to the database
     * in order to retrieve documents they own.
     */
    @JsonProperty("access_token")
    val accessToken: String,

    /**
     * User's display name (first name + surname), only used for client-side personalization.
     */
    @JsonProperty("display_name")
    val name: String,

    /**
     * User's email address for authentication purposes.
     */
    @JsonProperty("email")
    val email: String,

    /**
     * User's password for authentication purposes. At the moment, this password is stored
     * in plain-text!
     */
    @JsonProperty("password")
    val password: String
) : BaseModel() {
    /**
     * Overridden method from the BaseModel class declaring login to be type 2.
     */
    override fun modelType(): Int = 2
}