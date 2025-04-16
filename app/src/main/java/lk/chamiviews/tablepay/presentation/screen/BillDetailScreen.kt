package lk.chamiviews.tablepay.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import lk.chamiviews.tablepay.domain.model.Product
import lk.chamiviews.tablepay.presentation.screen.components.ProductItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BillDetailScreen(
    products: List<Product> = emptyList(),
    onBackPress: () -> Unit
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text("Bill Details Screen")
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
        LazyColumn(modifier = Modifier.fillMaxSize().padding(innerPadding).padding( 16.dp)) {
            items(products.size) {
                ProductItem(product = products[it])
            }
        }
    }

}

@Preview(showBackground = true)
@Composable
fun BillDetailScreenPreview() {
    BillDetailScreen(onBackPress = {})
}