package components

import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable

@Composable
fun NumberInputField(
    label: String,
    state: NumberInputFieldState,
    onValueChange: (String) -> Unit,
) {
    TextField(
        state.getInput(),
        label = { Text(label) },
        onValueChange = { onValueChange(it) },
        maxLines = 1,
        isError = !state.isValid()
    )
}