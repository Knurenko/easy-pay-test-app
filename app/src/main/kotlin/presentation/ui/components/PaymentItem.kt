package presentation.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.knurenko.easypaytestapp.R
import domain.entity.PaymentModel
import java.util.concurrent.TimeUnit

/**
 * @author Knurenko Bogdan 30.11.2023
 */

@Composable
fun PaymentItem(modifier: Modifier = Modifier, item: PaymentModel) =
    ConstraintLayout(
        modifier = modifier
            .fillMaxWidth()
            .shadow(elevation = 4.dp, shape = RoundedCornerShape(8.dp))
            .background(color = MaterialTheme.colorScheme.surface, shape = RoundedCornerShape(8.dp))
            .padding(8.dp)
    ) {
        val (title, date, amount) = createRefs()

        Title(
            modifier = Modifier.constrainAs(title) {
                top.linkTo(parent.top)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                width = Dimension.fillToConstraints
            },
            value = item.title
        )

        Amount(
            modifier = Modifier.constrainAs(amount) {
                start.linkTo(parent.start)
                bottom.linkTo(parent.bottom)
                top.linkTo(title.bottom)
            },
            value = item.amount
        )

        Date(
            modifier = Modifier.constrainAs(date) {
                end.linkTo(parent.end)
                bottom.linkTo(parent.bottom)
            },
            value = item.createdAt
        )
    }

@Composable
private fun Title(modifier: Modifier, value: String) = Text(
    modifier = modifier,
    text = value,
    style = TextStyle(
        color = MaterialTheme.colorScheme.onSurface,
        fontSize = 18.sp
    )
)

@Composable
private fun Date(modifier: Modifier, value: Long?) = Text(
    modifier = modifier,
    text = value?.calculateElapsedDaysFrom()?.let {
        pluralStringResource(R.plurals.days_ago, it, it)
    } ?: stringResource(R.string.payment_item_unknown_date),
    style = TextStyle(
        color = (value?.let { MaterialTheme.colorScheme.onSurface }
            ?: MaterialTheme.colorScheme.error).copy(alpha = .5f),
        fontSize = 18.sp
    )
)

@Composable
private fun Amount(modifier: Modifier, value: String?) = Text(
    modifier = modifier,
    text = value ?: stringResource(R.string.payment_item_unknown_amount),
    style = TextStyle(
        color = (value?.let { MaterialTheme.colorScheme.primary }
            ?: MaterialTheme.colorScheme.error).copy(alpha = .5f),
        fontSize = 18.sp
    )
)

private fun Long.calculateElapsedDaysFrom(): Int {
    return ((System.currentTimeMillis() - this * 1000) / MILLIS_IN_DAY).toInt()
}

private val MILLIS_IN_DAY = TimeUnit.DAYS.toMillis(1)


@Preview(showBackground = true, backgroundColor = 0xFFFFFF)
@Composable
private fun Preview() = Box(modifier = Modifier.padding(8.dp)) {
    PaymentItem(
        item = PaymentModel(
            id = 1,
            title = "Test title 4651",
            amount = "7832165",
            createdAt = 1684741518
        )
    )
}