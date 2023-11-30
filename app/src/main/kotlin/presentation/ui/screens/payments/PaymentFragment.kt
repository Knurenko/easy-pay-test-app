package presentation.ui.screens.payments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import data.storage.UserTokenStorage

/**
 * @author Knurenko Bogdan 30.11.2023
 */
class PaymentFragment(
    private val viewModel: PaymentScreenViewModel,
    private val userTokenStorage: UserTokenStorage
) : Fragment() {

    private val userToken = requireNotNull(userTokenStorage.userToken)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = ComposeView(requireContext()).apply {
        setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
        setContent {
            PaymentScreen(
                state = viewModel.state.collectAsState().value,
                onLogoutPress = {
                    userTokenStorage.userToken = null
                    findNavController().navigateUp()
                },
                onRefreshPress = { viewModel.fetchPayments(userToken) }
            )
        }
    }
}