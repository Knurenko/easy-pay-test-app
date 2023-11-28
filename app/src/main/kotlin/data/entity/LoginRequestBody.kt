package data.entity

import com.google.gson.annotations.SerializedName

/**
 * @author Knurenko Bogdan 28.11.2023
 */

data class LoginRequestBody(
    @SerializedName("login")
    val login: String,
    @SerializedName("password")
    val password: String
)
