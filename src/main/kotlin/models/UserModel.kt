package models

import com.fasterxml.jackson.annotation.JsonProperty

data class UserModel(
    @JsonProperty("id")
    val id: String,

    @JsonProperty("access_token")
    val accessToken: String,

    @JsonProperty("display_name")
    val name: String,

    @JsonProperty("email")
    val email: String,

    @JsonProperty("password")
    val password: String
) : BaseModel() {
    override fun modelType(): Int = 3
}