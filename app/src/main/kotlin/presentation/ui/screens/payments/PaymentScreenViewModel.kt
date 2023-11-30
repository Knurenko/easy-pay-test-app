package presentation.ui.screens.payments

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import domain.repo.PaymentRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * @author Knurenko Bogdan 30.11.2023
 */
class PaymentScreenViewModel(private val paymentRepository: PaymentRepository) : ViewModel() {
    private val _state: MutableStateFlow<PaymentScreenState> = MutableStateFlow(PaymentScreenState.Initial)
    val state = _state.asStateFlow()

    fun fetchPayments(token: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _state.emit(PaymentScreenState.Loading)

            try {
                val payments = paymentRepository.getPaymentsOfUser(token)
                _state.emit(PaymentScreenState.Success(payments))
            } catch (e: Exception) {
                _state.emit(PaymentScreenState.Failure(e.message ?: "Something's gone wrong"))
            }
        }
    }
}