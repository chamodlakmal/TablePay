package lk.chamiviews.tablepay.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import lk.chamiviews.tablepay.domain.model.Product
import lk.chamiviews.tablepay.presentation.events.BillDetailEvent
import lk.chamiviews.tablepay.presentation.screen.components.ProductItem
import lk.chamiviews.tablepay.R
import lk.chamiviews.tablepay.presentation.state.ProductDetailState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BillDetailScreen(
    products: List<Product> = emptyList(),
    productDetailState: ProductDetailState,
    onBackPress: () -> Unit,
    onEvent: (BillDetailEvent) -> Unit
) {
    val productIds = remember(products) { products.map { it.id } }
    LaunchedEffect(productIds) {
        onEvent(BillDetailEvent.GetProductDetails(productIds))
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(R.string.bill_details))
                }, navigationIcon = {
                    IconButton(onClick = {
                        onBackPress()
                    }) {
                        Icon(
                            Icons.AutoMirrored.Default.ArrowBack,
                            contentDescription = "back arrow"
                        )
                    }
                },
                modifier = Modifier
                    .shadow(elevation = 2.dp)
                    .background(Color.White)
            )
        }
    ) { innerPadding ->
        when (productDetailState) {
            is ProductDetailState.Error -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = productDetailState.message,
                        color = Color.Red,
                        textAlign = TextAlign.Center
                    )
                }
            }

            ProductDetailState.Loading -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            is ProductDetailState.Success -> {
                val productDetailsMap = productDetailState.productDetails.associateBy { it.id }

                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                        .padding(16.dp)
                ) {
                    items(products.size) { index ->
                        ProductItem(
                            product = products[index],
                            title = productDetailsMap[products[index].id]?.title ?: ""
                        )
                    }
                }
            }
        }

    }

}

@Preview(showBackground = true)
@Composable
fun BillDetailScreenPreview() {
    val products = listOf(
        Product(id = 1, price = 10.0, quantity = 2),
        Product(id = 2, price = 20.0, quantity = 1)
    )
    BillDetailScreen(
        products = products,
        productDetailState = ProductDetailState.Loading,
        onBackPress = {},
        onEvent = {})
}