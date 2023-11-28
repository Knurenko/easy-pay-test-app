package data.mappers

import data.entity.PaymentResponse
import domain.entity.PaymentModel

/**
 * @author Knurenko Bogdan 28.11.2023
 */
class PaymentResponseToModelMapper {
    fun map(response: PaymentResponse): PaymentModel = PaymentModel(
        id = response.id,
        title = response.title,
        amount = response.amount?.takeIf { it.isNotEmpty() },
        createdAt = response.createdAt
    )
}