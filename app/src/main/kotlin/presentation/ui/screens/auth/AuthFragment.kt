package presentation.ui.screens.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment

/**
 * @author Knurenko Bogdan 29.11.2023
 */
class AuthFragment(private val authScreenViewModel: AuthScreenViewModel) : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = ComposeView(requireContext()).apply {
        setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
        setContent {
            AuthScreen(
                screenState = authScreenViewModel.state.collectAsState().value,
                onAuthPress = { login, pass -> authScreenViewModel.performAuth(login, pass) },
                onAuthSuccess = {
                    // todo navigate to another fragment
                }
            )
        }
    }
}