package data.entity

import com.google.gson.annotations.SerializedName

/**
 * @author Knurenko Bogdan 28.11.2023
 */
data class LoginResponse(
    @SerializedName("token")
    val token: String
)
