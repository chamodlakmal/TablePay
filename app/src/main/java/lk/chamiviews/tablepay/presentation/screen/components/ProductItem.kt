package lk.chamiviews.tablepay.presentation.screen.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import lk.chamiviews.tablepay.domain.model.Product

@Composable
fun ProductItem(product: Product, title: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Title : ${title}",
                modifier = Modifier.padding(bottom = 4.dp)
            )
            Text(
                text = "Quantity : ${product.quantity}",
                modifier = Modifier.padding(bottom = 4.dp)
            )
            Text(text = "Price : ${product.price}")
        }

    }
}

@Preview
@Composable
fun ProductItemPreview() {
    val product = Product(
        id = 1,
        price = 100.0,
        quantity = 2
    )
    ProductItem(product = product, title = "Product Title")
}