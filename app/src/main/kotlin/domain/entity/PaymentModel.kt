package domain.entity

/**
 * @author Knurenko Bogdan 28.11.2023
 */
data class PaymentModel(
    val id: Int,
    val title: String,
    val amount: String?,
    val createdAt: Long?
)
