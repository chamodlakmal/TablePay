package lk.chamiviews.tablepay.presentation.screen.components

import android.hardware.camera2.params.BlackLevelPattern
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import lk.chamiviews.tablepay.domain.model.Cart
import lk.chamiviews.tablepay.ui.theme.Green
import lk.chamiviews.tablepay.ui.theme.LightMint
import java.util.Locale

@Composable
fun CartItem(cart: Cart, onClick: () -> Unit) {
    val backgroundColor = if (cart.isPaid) LightMint else Color.White
    val borderColor = if (cart.isPaid) Green else Color.LightGray

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(4.dp),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = backgroundColor
        ),
        border = BorderStroke(1.dp, borderColor)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "Table Id : ${cart.id}",
                    style = MaterialTheme.typography.bodyLarge.copy(color = Color.Black),
                    modifier = Modifier.weight(1f)
                )

                if (cart.isPaid) {
                    Icon(
                        imageVector = Icons.Default.CheckCircle,
                        contentDescription = "Paid",
                        tint = Green,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Total Bill Amount : ${String.format(Locale.US, "%.2f", cart.total)}",
                style = MaterialTheme.typography.bodyMedium.copy(color = Color.Black)
            )
        }

    }

}

@Preview
@Composable
fun CartItemPreview() {
    val cart = Cart(
        id = 1,
        total = 100.0,
        products = emptyList(),
        isPaid = false
    )
    CartItem(cart = cart, onClick = {})
}