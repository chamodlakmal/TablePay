package lk.chamiviews.tablepay.presentation.screen.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import lk.chamiviews.tablepay.R
import lk.chamiviews.tablepay.domain.model.Product
import lk.chamiviews.tablepay.domain.model.ProductDetail
import lk.chamiviews.tablepay.presentation.state.ProductDetailState
import java.util.Locale

@Composable
fun ProductItem(
    product: Product,
    productDetailState: ProductDetailState
) {
    val colors = MaterialTheme.colorScheme
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = colors.surfaceVariant)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 12.dp)
            ) {
                when (productDetailState) {
                    is ProductDetailState.Loading -> {
                        Text(
                            text = "Loading...",
                            style = MaterialTheme.typography.bodyMedium.copy(color = colors.onSurfaceVariant)
                        )
                    }

                    is ProductDetailState.Success -> {
                        Text(
                            text = productDetailState.productDetail.title,
                            style = MaterialTheme.typography.titleMedium.copy(
                                color = colors.primary
                            ),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            modifier = Modifier.padding(bottom = 4.dp)
                        )
                    }

                    is ProductDetailState.Error -> {
                        Text(
                            text = "Error: ${productDetailState.message}",
                            style = MaterialTheme.typography.bodyMedium.copy(color = colors.error),
                            overflow = TextOverflow.Ellipsis,
                            maxLines = 1
                        )
                    }
                }

                Text(
                    text = "Quantity: ${product.quantity}",
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = colors.onSurfaceVariant
                    ),
                    modifier = Modifier.padding(bottom = 2.dp)
                )

                Text(
                    text = "Price: ${String.format(Locale.US, "%.2f", product.price)}",
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = colors.onSurfaceVariant
                    )
                )
            }

            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(product.thumbnail)
                    .crossfade(true)
                    .build(),
                placeholder = painterResource(R.drawable.ic_image_placeholder),
                error = painterResource(R.drawable.ic_image_loading_failed),
                contentDescription = "Product Image ${product.id}",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(8.dp))
            )
        }
    }
}

@Preview
@Composable
fun ProductItemPreview() {
    val product = Product(
        id = 1,
        price = 100.0,
        quantity = 2,
        thumbnail = ""
    )
    val productDetailState =
        ProductDetailState.Success(productDetail = ProductDetail(id = 1, title = "Product 1"))
    ProductItem(product = product, productDetailState = productDetailState)
}