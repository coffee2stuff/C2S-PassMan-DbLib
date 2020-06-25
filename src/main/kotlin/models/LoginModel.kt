package models

import com.fasterxml.jackson.annotation.JsonProperty

data class LoginModel(
    @JsonProperty("id")
    val id: String,

    @JsonProperty("access_token")
    val accessToken: String,

    @JsonProperty("name")
    val loginName: String,

    @JsonProperty("username")
    val username: String,

    @JsonProperty("password")
    val password: String,

    @JsonProperty("url")
    val loginURL: String
) : Model() {
    override fun determinModelType(): Int = 1
}