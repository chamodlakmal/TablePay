package lk.chamiviews.tablepay.presentation.screen.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun ButtonWithLoading(isLoading: Boolean, onCLick: () -> Unit) {
    Button(
        enabled = !isLoading,
        onClick = onCLick,
        modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text("Mark as Paid")
            if (isLoading) {
                CircularProgressIndicator(modifier = Modifier.padding(start = 24.dp))
            }
        }

    }

}

@Preview(showBackground = true)
@Composable
fun ButtonWithLoadingPreview() {
    ButtonWithLoading(isLoading = true, onCLick = {})
}
