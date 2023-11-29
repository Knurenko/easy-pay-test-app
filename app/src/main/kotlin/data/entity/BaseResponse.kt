package data.entity

import com.google.gson.annotations.SerializedName

/**
 * @author Knurenko Bogdan 28.11.2023
 */
data class BaseResponse<T>(
    @SerializedName("success")
    val success: Boolean,
    @SerializedName("response")
    val response: T,
    @SerializedName("error")
    val error: ErrorBody?
)

data class ErrorBody(
    @SerializedName("error_code")
    val code: Int,
    @SerializedName("error_msg")
    val msg: String
)