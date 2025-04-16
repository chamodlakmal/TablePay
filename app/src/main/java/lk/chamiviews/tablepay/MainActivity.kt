package lk.chamiviews.tablepay

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
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
                        val viewModel = hiltViewModel<CartViewModel>()
                        val carts = viewModel.cartPagingFlow.collectAsLazyPagingItems()
                        CartsScreen(carts = carts, navigateToBillDetail = { cart ->
                            val productsJson = Json.encodeToString(cart.products)
                            navController.navigate(
                                BillDetailScreen(productsJson)
                            )
                        })
                    }
                    composable<BillDetailScreen> {
                        val args = it.toRoute<BillDetailScreen>()
                        BillDetailScreen(
                            products = Json.decodeFromString(args.productsJson),
                            onBackPress = {
                                navController.popBackStack()
                            })
                    }

                }


            }
        }
    }
}

@Serializable
object CartsScreen

@Serializable
data class BillDetailScreen(val productsJson: String)
