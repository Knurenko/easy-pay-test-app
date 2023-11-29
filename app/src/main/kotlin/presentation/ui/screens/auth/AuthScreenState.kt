package presentation.ui.screens.auth

/**
 * @author Knurenko Bogdan 29.11.2023
 */
sealed class AuthScreenState {
    object Initial : AuthScreenState()
    object Loading : AuthScreenState()
    data class Success(val token: String) : AuthScreenState()
    data class Failure(val message: String) : AuthScreenState()
}
