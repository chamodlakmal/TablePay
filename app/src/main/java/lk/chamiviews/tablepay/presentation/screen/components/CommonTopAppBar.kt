package lk.chamiviews.tablepay.presentation.screen.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CommonTopAppBar(title: String, showIcon: Boolean = false, onClick: () -> Unit = {}) {
    Surface(shadowElevation = 4.dp) {
        TopAppBar(title = {
            Text(
                text = title
            )
        }, navigationIcon = {
            if (showIcon) {
                IconButton(onClick = onClick) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                }
            }
        })
    }
}

@Preview(showBackground = true)
@Composable
private fun CommonTopAppBarPreview() {
    CommonTopAppBar(title = "Preview", onClick = {})
}