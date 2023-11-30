package presentation.ui.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.knurenko.easypaytestapp.R

/**
 * @author Knurenko Bogdan 30.11.2023
 */

@Composable
fun LogoutConfirmationDialog(onConfirm: () -> Unit, onDismiss: () -> Unit) = AlertDialog(
    icon = {
        Icon(
            painter = painterResource(R.drawable.ic_logout),
            contentDescription = "Logout icon",
            tint = MaterialTheme.colorScheme.primary
        )
    },
    title = {
        Text(
            text = stringResource(R.string.logout_dialog_message),
            style = TextStyle(textAlign = TextAlign.Center)
        )
    },
    onDismissRequest = onDismiss,
    confirmButton = {
        TextButton(onClick = onConfirm) {
            Text(text = stringResource(R.string.logout_dialog_yes))
        }
    },
    dismissButton = {
        TextButton(onClick = onDismiss) {
            Text(text = stringResource(R.string.logout_dialog_no))
        }
    }
)

@Preview
@Composable
private fun Preview() = LogoutConfirmationDialog({}, {})