package lk.chamiviews.tablepay

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import androidx.paging.compose.collectAsLazyPagingItems
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import lk.chamiviews.tablepay.presentation.screen.BillDetailScreen
import lk.chamiviews.tablepay.presentation.screen.CartsScreen
import lk.chamiviews.tablepay.presentation.viewmodel.BillDetailViewModel
import lk.chamiviews.tablepay.presentation.viewmodel.CartViewModel
import lk.chamiviews.tablepay.ui.theme.TablePayTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TablePayTheme {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = CartsScreen
                ) {

                    composable<CartsScreen> {
                        val cartViewModel = hiltViewModel<CartViewModel>()
                        val carts = cartViewModel.cartPagingFlow.collectAsLazyPagingItems()
                        CartsScreen(carts = carts, navigateToBillDetail = { cart ->
                            val cartJson = Json.encodeToString(cart)
                            navController.navigate(
                                BillDetailScreen(cartJson)
                            )
                        })
                    }
                    composable<BillDetailScreen> {
                        val billDetailViewModel = hiltViewModel<BillDetailViewModel>()
                        val args = it.toRoute<BillDetailScreen>()
                        val productDetailsValue =
                            billDetailViewModel.productDetailState.collectAsState()
                        BillDetailScreen(
                            cart = Json.decodeFromString(args.cartJson),
                            productIdWiseProductDetailState = productDetailsValue.value,
                            onBackPress = {
                                navController.popBackStack()
                            }, onEvent = billDetailViewModel::onEvent,
                            markCartAsPaidState = billDetailViewModel.markCartAsPaidState.collectAsState().value
                        )
                    }

                }


            }
        }
    }
}

@Serializable
object CartsScreen

@Serializable
data class BillDetailScreen(val cartJson: String)
