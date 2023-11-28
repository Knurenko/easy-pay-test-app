package data.repo

import data.mappers.PaymentResponseToModelMapper
import data.network.EasyPayService
import domain.entity.PaymentModel
import domain.repo.PaymentRepository

/**
 * @author Knurenko Bogdan 28.11.2023
 */
class PaymentRepoImpl(
    private val service: EasyPayService,
    private val mapper: PaymentResponseToModelMapper
) : PaymentRepository {
    override suspend fun getPaymentsOfUser(userToken: String): List<PaymentModel> {
        val payments = service.fetchPaymentsForUser(userToken).response
        return payments.map { mapper.map(it) }
    }
}