package presentation.ui.screens.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.knurenko.easypaytestapp.R

/**
 * @author Knurenko Bogdan 29.11.2023
 */

@Composable
fun AuthScreen(
    screenState: AuthScreenState,
    onAuthPress: (String, String) -> Unit,
    onAuthSuccess: (String) -> Unit
) {
    var loginStr: String by rememberSaveable { mutableStateOf("") }
    var passwordStr: String by rememberSaveable { mutableStateOf("") }

    var isPasswordVisible: Boolean by remember { mutableStateOf(false) }
    val onAuthClick = { onAuthPress(loginStr, passwordStr) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background)
    ) {
        Column(
            modifier = Modifier.align(Alignment.Center),
            horizontalAlignment = CenterHorizontally
        ) {
            Image(
                painter = painterResource(R.drawable.logo_pic),
                contentDescription = "logo",
                colorFilter = ColorFilter.tint(color = MaterialTheme.colorScheme.primary)
            )

            Spacer(modifier = Modifier.height(64.dp))

            OutlinedTextField(
                value = loginStr,
                onValueChange = { loginStr = it },
                label = {
                    Text(
                        text = stringResource(R.string.auth_login_label),
                        style = TextStyle(
                            color = MaterialTheme.colorScheme.primary,
                            fontSize = 18.sp
                        )
                    )
                },
                textStyle = TextStyle(fontSize = 18.sp)
            )

            OutlinedTextField(
                value = passwordStr,
                onValueChange = { passwordStr = it },
                visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                label = {
                    Text(
                        text = stringResource(R.string.auth_password_label),
                        style = TextStyle(
                            color = MaterialTheme.colorScheme.primary,
                            fontSize = 18.sp
                        )
                    )
                },
                trailingIcon = {
                    PasswordVisibilityIcon(
                        isPasswordVisible = isPasswordVisible,
                        onToggleVisibility = { isPasswordVisible = !isPasswordVisible }
                    )
                },
                textStyle = TextStyle(fontSize = 18.sp)
            )

            Spacer(modifier = Modifier.height(24.dp))

            when (screenState) {
                AuthScreenState.Initial -> EnabledAuthButton(onAuthClick)
                AuthScreenState.Loading -> LoadingAuthButton()
                is AuthScreenState.Success -> {
                    AuthSuccessfulButton()

                    SideEffect { onAuthSuccess(screenState.token) }
                }

                is AuthScreenState.Failure -> {
                    EnabledAuthButton(onAuthClick)

                    Spacer(modifier = Modifier.height(16.dp))
                    ErrorMessage(screenState.message)
                }
            }
        }
    }
}


@Composable
private fun EnabledAuthButton(onAuthPress: () -> Unit) = Button(
    modifier = Modifier.width(240.dp),
    onClick = onAuthPress,
    content = {
        Text(text = stringResource(R.string.auth_button_text), style = TextStyle(fontSize = 18.sp))
    },
    enabled = true
)

@Composable
private fun LoadingAuthButton() = Button(
    modifier = Modifier.width(240.dp),
    onClick = { },
    content = {
        CircularProgressIndicator(modifier = Modifier.size(24.dp), strokeWidth = 2.dp)
    },
    enabled = false
)

@Composable
private fun AuthSuccessfulButton() = Button(
    modifier = Modifier.width(240.dp),
    onClick = { },
    content = {
        Icon(
            modifier = Modifier.size(24.dp),
            painter = painterResource(R.drawable.ic_auth_success),
            contentDescription = "Auth successful",
            tint = MaterialTheme.colorScheme.primary
        )
    },
    enabled = false
)

@Composable
private fun ErrorMessage(msg: String) = Row(modifier = Modifier.padding(horizontal = 16.dp)) {
    Icon(
        modifier = Modifier.align(CenterVertically),
        painter = painterResource(R.drawable.ic_error),
        contentDescription = "Error sign",
        tint = MaterialTheme.colorScheme.error
    )
    Text(
        modifier = Modifier.padding(start = 8.dp),
        text = msg,
        style = TextStyle(
            color = MaterialTheme.colorScheme.error,
            fontSize = 16.sp,
            textAlign = TextAlign.Center
        )
    )
}

@Composable
private fun PasswordVisibilityIcon(isPasswordVisible: Boolean, onToggleVisibility: () -> Unit) {
    Box(modifier = Modifier
        .clip(RoundedCornerShape(16.dp))
        .clickable { onToggleVisibility() }) {
        Icon(
            painter = painterResource(id = if (isPasswordVisible) R.drawable.ic_eye_open else R.drawable.ic_eye_closed),
            contentDescription = "password visibility",
            tint = MaterialTheme.colorScheme.primary
        )
    }
}

@Preview
@Composable
private fun Preview() = AuthScreen(
    AuthScreenState.Success("Something's gone wrong"),
    { _, _ -> },
    {}
)