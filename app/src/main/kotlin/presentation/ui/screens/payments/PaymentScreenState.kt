package presentation.ui.screens.payments

import domain.entity.PaymentModel

/**
 * @author Knurenko Bogdan 30.11.2023
 */
sealed class PaymentScreenState {
    object Initial : PaymentScreenState()
    object Loading : PaymentScreenState()
    data class Success(val paymentsToShow: List<PaymentModel>) : PaymentScreenState()
    data class Failure(val message: String) : PaymentScreenState()
}
