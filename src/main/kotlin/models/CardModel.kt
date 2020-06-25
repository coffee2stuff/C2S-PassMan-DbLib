package models

import com.fasterxml.jackson.annotation.JsonProperty

data class CardModel(
    @JsonProperty("id")
    val id: String,

    @JsonProperty("access_token")
    val accessToken: String,

    @JsonProperty("name")
    val cardName: String,

    @JsonProperty("cardholder")
    val cardholderName: String,

    @JsonProperty("card_number")
    val cardNumber: String,

    @JsonProperty("card_brand")
    val brand: String,

    @JsonProperty("exp_month")
    val expirationMonth: Int,

    @JsonProperty("exp_year")
    val expirationYear: Int,

    @JsonProperty("csc")
    val securityCode: Int,

    @JsonProperty("pin")
    val pinNumber: Int
) : BaseModel() {
    override fun modelType(): Int = 0
}