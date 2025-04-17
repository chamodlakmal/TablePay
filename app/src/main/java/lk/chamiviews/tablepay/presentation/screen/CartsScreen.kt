package lk.chamiviews.tablepay.presentation.screen

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import kotlinx.coroutines.flow.flowOf
import lk.chamiviews.tablepay.R
import lk.chamiviews.tablepay.domain.model.Cart
import lk.chamiviews.tablepay.domain.model.Product
import lk.chamiviews.tablepay.presentation.screen.components.CartItem
import lk.chamiviews.tablepay.presentation.screen.components.CommonTopAppBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartsScreen(carts: LazyPagingItems<Cart>, navigateToBillDetail: (cart: Cart) -> Unit) {
    val listState = rememberLazyListState()
    val context = LocalContext.current
    LaunchedEffect(key1 = carts.loadState) {
        if (carts.loadState.refresh is LoadState.Error) {
            Toast.makeText(
                context,
                "Error: " + (carts.loadState.refresh as LoadState.Error).error.message,
                Toast.LENGTH_LONG
            ).show()
        }

        if (carts.loadState.append is LoadState.Error) {
            Toast.makeText(
                context,
                "Error: " + (carts.loadState.append as LoadState.Error).error.message,
                Toast.LENGTH_LONG
            ).show()
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            CommonTopAppBar(
                title = stringResource(R.string.tables),
                showIcon = false
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(
                    innerPadding
                )
                .fillMaxSize()
        ) {
            when {
                carts.loadState.refresh is LoadState.Loading -> {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center)
                    )
                }

                carts.itemCount == 0 && carts.loadState.refresh !is LoadState.Error -> {
                    Text(
                        text = stringResource(R.string.no_carts_available),
                        modifier = Modifier.align(Alignment.Center)
                    )
                }

                else -> {
                    LazyColumn(
                        state = listState,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        items(
                            count = carts.itemCount,
                            key = carts.itemKey { it.id }) { index ->
                            val cart = carts[index]
                            if (cart != null) {
                                CartItem(cart = cart, onClick = {
                                    navigateToBillDetail(cart)
                                })
                            }
                        }
                        item {
                            if (carts.loadState.append is LoadState.Loading) {
                                CircularProgressIndicator()
                            }
                        }
                    }
                }
            }
        }


    }

}

@Preview
@Composable
fun CartsScreenPreview() {
    val sampleCarts = listOf(
        Cart(
            id = 1,
            total = 120.0,
            products = listOf(
                Product(id = 1, price = 10.0, quantity = 2, thumbnail = ""),
                Product(id = 2, price = 20.0, quantity = 1, thumbnail = "")
            ),
            isPaid = false
        ),
        Cart(
            id = 2,
            total = 250.0,
            products = listOf(
                Product(id = 3, price = 25.0, quantity = 3, thumbnail = "")
            ),
            isPaid = true
        )
    )

    val lazyPagingItems = sampleCarts.fakeLazyPagingItems()
    CartsScreen(carts = lazyPagingItems) { }
}

@Composable
fun <T : Any> List<T>.fakeLazyPagingItems(): LazyPagingItems<T> {
    val pagingData = remember { PagingData.from(this) }
    return flowOf(pagingData).collectAsLazyPagingItems()
}
