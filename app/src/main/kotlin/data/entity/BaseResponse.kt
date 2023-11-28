package data.entity

import com.google.gson.annotations.SerializedName

/**
 * @author Knurenko Bogdan 28.11.2023
 */
data class BaseResponse<T>(
    @SerializedName("success")
    val success: Boolean,
    @SerializedName("response")
    val response: T
)