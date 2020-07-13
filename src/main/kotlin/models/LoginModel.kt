package models

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

/**
 * Login model
 */
@JsonIgnoreProperties(value = ["_id"])
data class LoginModel(
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
     * Human-readable name, or identification, of this login.
     */
    @JsonProperty("name")
    val loginName: String,

    /**
     * Username that the user wants to store.
     */
    @JsonProperty("username")
    val username: String,

    /**
     * Corresponding password to aforementioned username.
     */
    @JsonProperty("password")
    val password: String,

    /**
     * URL of the site at which this login is being used.
     */
    @JsonProperty("url")
    val loginURL: String
) : BaseModel() {
    /**
     * Overridden method from the BaseModel class declaring login to be type 0.
     */
    override fun modelType(): Int = 0
}