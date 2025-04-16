package lk.chamiviews.tablepay.presentation.screen

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import lk.chamiviews.tablepay.R
import lk.chamiviews.tablepay.domain.model.Cart
import lk.chamiviews.tablepay.domain.model.Product
import lk.chamiviews.tablepay.domain.model.ProductDetail
import lk.chamiviews.tablepay.presentation.events.BillDetailEvent
import lk.chamiviews.tablepay.presentation.screen.components.ButtonWithLoading
import lk.chamiviews.tablepay.presentation.screen.components.CommonTopAppBar
import lk.chamiviews.tablepay.presentation.screen.components.PaidComponent
import lk.chamiviews.tablepay.presentation.screen.components.ProductItem
import lk.chamiviews.tablepay.presentation.state.MarkCartAsPaidState
import lk.chamiviews.tablepay.presentation.state.ProductDetailState
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BillDetailScreen(
    cart: Cart,
    productIdWiseProductDetailState: HashMap<Int, ProductDetailState>,
    onBackPress: () -> Unit,
    onEvent: (BillDetailEvent) -> Unit,
    markCartAsPaidState: MarkCartAsPaidState
) {
    val context = LocalContext.current
    val productIds = remember(cart.products) { cart.products.map { it.id } }
    LaunchedEffect(productIds) {
        onEvent(BillDetailEvent.GetProductDetails(productIds))
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(), topBar = {
            CommonTopAppBar(
                title = stringResource(R.string.bill_details, cart.id), showIcon = true, onClick = {
                    onBackPress()
                })
        }) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            if(cart.products.isEmpty()){
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                        .padding(32.dp),
                ) {
                    Text(
                        text = stringResource(R.string.no_products_found), // Add this to your strings.xml
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }else{
                LazyColumn(
                    modifier = Modifier
                        .padding(top = 8.dp)
                        .weight(1f)
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                ) {
                    items(cart.products.size) { index ->
                        ProductItem(
                            product = cart.products[index],
                            productDetailState = productIdWiseProductDetailState[cart.products[index].id]
                                ?: ProductDetailState.Loading
                        )
                    }
                }
                Column(
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Surface(
                        tonalElevation = 4.dp, shadowElevation = 4.dp
                    ) {
                        Text(
                            text = "Total Bill Amount: ${String.format(Locale.US, "%.2f", cart.total)}",
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp),
                            textAlign = TextAlign.Center
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))

                    when (markCartAsPaidState) {
                        is MarkCartAsPaidState.Error -> {
                            Toast.makeText(context, markCartAsPaidState.message, Toast.LENGTH_LONG)
                                .show()
                            ButtonWithLoading(isLoading = false, onCLick = {
                                onEvent(BillDetailEvent.MarkAsPaid(cart.id))
                            })
                        }

                        MarkCartAsPaidState.Loading -> {
                            ButtonWithLoading(isLoading = true, onCLick = {})
                        }

                        is MarkCartAsPaidState.Success -> {
                            PaidComponent()
                        }

                        MarkCartAsPaidState.Idle -> {
                            if (cart.isPaid) {
                                PaidComponent()
                            } else {
                                ButtonWithLoading(
                                    isLoading = markCartAsPaidState is MarkCartAsPaidState.Loading,
                                    onCLick = {
                                        onEvent(BillDetailEvent.MarkAsPaid(cart.id))
                                    })
                            }
                        }
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
        Product(id = 1, price = 10.0, quantity = 2, thumbnail = ""),
        Product(id = 2, price = 20.0, quantity = 1, thumbnail = "")
    )
    val cart = Cart(
        id = 1, total = 120.0, products = emptyList(), isPaid = false
    )
    val mockProductState = hashMapOf(
        1 to ProductDetailState.Success(ProductDetail(id = 1, title = "Product 1")),
        2 to ProductDetailState.Loading
    )
    BillDetailScreen(
        cart = cart,
        productIdWiseProductDetailState = mockProductState,
        onBackPress = {},
        onEvent = {},
        markCartAsPaidState = MarkCartAsPaidState.Success("ds")
    )
}