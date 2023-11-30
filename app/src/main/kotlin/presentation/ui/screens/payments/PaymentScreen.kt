package presentation.ui.screens.payments

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.knurenko.easypaytestapp.R
import domain.entity.PaymentModel
import presentation.ui.components.PaymentItem
import presentation.ui.components.PaymentsFragmentToolbar

/**
 * @author Knurenko Bogdan 30.11.2023
 */

@Composable
fun PaymentScreen(
    state: PaymentScreenState,
    onLogoutPress: () -> Unit,
    onRefreshPress: () -> Unit
) = Scaffold(
    topBar = { PaymentsFragmentToolbar(onLogoutPress, onRefreshPress) }
) {
    Box(modifier = Modifier.padding(it)) {
        when (state) {
            PaymentScreenState.Initial -> SideEffect { onRefreshPress() }
            PaymentScreenState.Loading -> LoadingView()
            is PaymentScreenState.Success -> PaymentListView(state.paymentsToShow)
            is PaymentScreenState.Failure -> ErrorMessageView(state.message)
        }
    }
}

@Composable
private fun PaymentListView(payments: List<PaymentModel>) = LazyColumn {
    items(payments) { PaymentItem(item = it, modifier = Modifier.padding(2.dp)) }
}

@Composable
private fun ErrorMessageView(message: String) = Box(
    modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)
) {
    Column(
        modifier = Modifier.align(Alignment.Center),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            modifier = Modifier.size(64.dp),
            painter = painterResource(R.drawable.ic_error),
            contentDescription = "Error icon",
            tint = MaterialTheme.colorScheme.error
        )
        Text(
            modifier = Modifier.padding(top = 8.dp),
            text = message,
            style = TextStyle(
                fontSize = 24.sp,
                color = MaterialTheme.colorScheme.error,
                textAlign = TextAlign.Center
            )
        )
    }
}

@Composable
private fun LoadingView() = Box(Modifier.fillMaxSize()) {
    CircularProgressIndicator(
        modifier = Modifier
            .align(Alignment.Center)
            .size(240.dp),
        color = MaterialTheme.colorScheme.primary,
        strokeWidth = 4.dp
    )
}

@Preview
@Composable
private fun Preview() {
    val list = listOf(
        PaymentModel(1, "Test 1", "798974", null),
        PaymentModel(2, "Test 2", "100", null),
        PaymentModel(3, "test 3", "123", null),
        PaymentModel(4, "test 4", null, null),
        PaymentModel(5, "test 5", "null", null),
        PaymentModel(6, "test  6", "444444444444", null)
    )
    val success = PaymentScreenState.Success(list)
    PaymentScreen(success, {}, {})
}