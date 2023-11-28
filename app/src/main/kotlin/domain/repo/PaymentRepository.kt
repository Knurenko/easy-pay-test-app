package domain.repo

import domain.entity.PaymentModel

/**
 * @author Knurenko Bogdan 28.11.2023
 */
interface PaymentRepository {
    suspend fun getPaymentsOfUser(userToken: String): List<PaymentModel>
}