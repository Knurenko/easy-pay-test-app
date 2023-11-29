package presentation.ui.screens.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import domain.repo.AuthRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * @author Knurenko Bogdan 29.11.2023
 */
class AuthScreenViewModel(
    private val authRepo: AuthRepository
) : ViewModel() {
    private val _state: MutableStateFlow<AuthScreenState> =
        MutableStateFlow(AuthScreenState.Initial)
    val state = _state.asStateFlow()

    fun performAuth(login: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _state.emit(AuthScreenState.Loading)

            try {
                val token = authRepo.auth(login, password)
                _state.emit(AuthScreenState.Success(token))
            } catch (e: Exception) {
                _state.emit(AuthScreenState.Failure(e.message ?: "Something's gone wrong"))
            }
        }
    }
}