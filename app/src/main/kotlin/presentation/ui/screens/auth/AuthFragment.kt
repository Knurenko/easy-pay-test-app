package presentation.ui.screens.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.knurenko.easypaytestapp.R
import data.storage.UserTokenStorage

/**
 * @author Knurenko Bogdan 29.11.2023
 */
class AuthFragment(
    private val viewModel: AuthScreenViewModel,
    private val userTokenStorage: UserTokenStorage
) : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = ComposeView(requireContext()).apply {
        setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
        setContent {
            AuthScreen(
                screenState = viewModel.state.collectAsState().value,
                onAuthPress = { login, pass -> viewModel.performAuth(login, pass) },
                onAuthSuccess = {
                    viewModel.clearState()
                    userTokenStorage.userToken = it
                    findNavController().navigate(R.id.action_authFragment_to_paymentFragment)
                }
            )
        }
    }
}