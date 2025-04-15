package lk.chamiviews.tablepay

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import dagger.hilt.android.AndroidEntryPoint
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
                val viewModel = hiltViewModel<CartViewModel>()
                val carts = viewModel.cartPagingFlow.collectAsLazyPagingItems()
                CartsScreen(carts = carts)

            }
        }
    }
}

