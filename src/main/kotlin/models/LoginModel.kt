package models

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(value = ["_id"])
data class LoginModel(
    @JsonProperty("id")
    val id: String?,

    @JsonProperty("access_token")
    val accessToken: String?,

    @JsonProperty("name")
    val loginName: String?,

    @JsonProperty("username")
    val username: String?,

    @JsonProperty("password")
    val password: String?,

    @JsonProperty("url")
    val loginURL: String?
) : BaseModel() {
    override fun modelType(): Int = 0
}