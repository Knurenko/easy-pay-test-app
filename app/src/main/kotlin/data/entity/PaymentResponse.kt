package data.entity

import com.google.gson.annotations.SerializedName

/**
 * @author Knurenko Bogdan 28.11.2023
 */
data class PaymentResponse(
    @SerializedName("id")
    val id: Int,
    @SerializedName("title")
    val title: String,
    @SerializedName("amount")
    val amount: String?,
    @SerializedName("created")
    val createdAt: Long?
)
