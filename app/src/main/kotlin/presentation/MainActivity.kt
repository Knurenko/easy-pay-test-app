package presentation

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.knurenko.easypaytestapp.R
import data.mappers.PaymentResponseToModelMapper
import data.network.di.RetrofitServiceFactory
import data.repo.AuthRepoImpl
import data.repo.PaymentRepoImpl
import domain.repo.AuthRepository
import domain.repo.PaymentRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * @author Knurenko Bogdan 28.11.2023
 */
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val service = RetrofitServiceFactory.create()
        val authRepo: AuthRepository = AuthRepoImpl(service)
        val paymentRepo: PaymentRepository =
            PaymentRepoImpl(service, PaymentResponseToModelMapper())

        setContent {
            var token by remember { mutableStateOf("") }
            val scope = rememberCoroutineScope()
            val context = LocalContext.current
            Content(
                onAuthPress = {
                    scope.launch(context = Dispatchers.IO) {
                        val retrievedToken = authRepo.auth(login = "demo", password = "12345")
                        token = retrievedToken

                        withContext(Dispatchers.Main) {
                            Toast
                                .makeText(context, "token = $retrievedToken", Toast.LENGTH_SHORT)
                                .show()
                        }
                    }
                },
                onPaymentFetchPress = {
                    scope.launch(context = Dispatchers.IO) {
                        token.takeIf { it.isNotEmpty() }?.let {
                            val payments = paymentRepo.getPaymentsOfUser(it)
                            payments.forEach { payment ->
                                Log.i("check", payment.toString())
                            }

                            withContext(Dispatchers.Main) {
                                Toast
                                    .makeText(
                                        context,
                                        "fetched ${payments.size} items",
                                        Toast.LENGTH_SHORT
                                    )
                                    .show()
                            }
                        }
                    }
                }
            )
        }
    }
}

@Composable
private fun Content(onAuthPress: () -> Unit, onPaymentFetchPress: () -> Unit) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = MaterialTheme.colorScheme.primary)
                    .padding(16.dp)
            ) {
                Text(
                    text = stringResource(R.string.app_name),
                    style = TextStyle(
                        color = MaterialTheme.colorScheme.onPrimary,
                        fontSize = 18.sp
                    )
                )
            }
        }
    ) {
        Column(modifier = Modifier.padding(it)) {
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .background(
                        color = MaterialTheme.colorScheme.primary,
                        shape = RoundedCornerShape(16.dp)
                    ),
                onClick = onAuthPress
            ) {
                Text(text = "Try to auth")
            }

            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .background(
                        color = MaterialTheme.colorScheme.primary,
                        shape = RoundedCornerShape(16.dp)
                    ),
                onClick = onPaymentFetchPress
            ) {
                Text(text = "Try to fetch payments")
            }
        }
    }
}

@Preview
@Composable
private fun Preview() = Content({}, {})