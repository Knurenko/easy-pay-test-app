package presentation.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.knurenko.easypaytestapp.R

/**
 * @author Knurenko Bogdan 30.11.2023
 */

@Composable
fun PaymentsFragmentToolbar(
    onLogout: () -> Unit,
    onRefreshPress: () -> Unit
) = Row(
    Modifier
        .fillMaxWidth()
        .background(color = MaterialTheme.colorScheme.primary, shape = RectangleShape)
        .padding(16.dp),
    verticalAlignment = Alignment.CenterVertically
) {
    var isLogoutConfirmationVisible: Boolean by remember { mutableStateOf(false) }
    Text(
        text = stringResource(R.string.payment_list_toolbar_title),
        style = TextStyle(fontSize = 24.sp, color = MaterialTheme.colorScheme.onPrimary)
    )
    Spacer(modifier = Modifier.weight(1f))
    RefreshIcon(onRefreshPress)
    LogoutIcon { isLogoutConfirmationVisible = true }

    if (isLogoutConfirmationVisible) {
        LogoutConfirmationDialog(onConfirm = {
            isLogoutConfirmationVisible = false
            onLogout()
        }, onDismiss = {
            isLogoutConfirmationVisible = false
        })
    }
}

@Composable
private fun LogoutIcon(onLogoutPress: () -> Unit) = Box(modifier = Modifier
    .clip(RoundedCornerShape(16.dp))
    .clickable { onLogoutPress() }) {
    Icon(
        modifier = Modifier
            .padding(4.dp)
            .size(24.dp),
        painter = painterResource(R.drawable.ic_logout),
        contentDescription = "Logout icon",
        tint = MaterialTheme.colorScheme.onPrimary
    )
}

@Composable
private fun RefreshIcon(onRefreshPress: () -> Unit) = Box(modifier = Modifier
    .clip(RoundedCornerShape(16.dp))
    .clickable { onRefreshPress() }) {
    Icon(
        modifier = Modifier
            .padding(4.dp)
            .size(24.dp),
        painter = painterResource(R.drawable.ic_refresh),
        contentDescription = "Refresh icon",
        tint = MaterialTheme.colorScheme.onPrimary
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun Preview() = Box(modifier = Modifier.fillMaxSize()) {
    PaymentsFragmentToolbar({}, {})
}