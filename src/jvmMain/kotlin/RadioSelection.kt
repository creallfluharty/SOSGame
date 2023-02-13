import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.selection.selectable
import androidx.compose.material.RadioButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
@Preview
fun RadioSelection(options: List<String>, selected: String, onOptionSelect: (String) -> Unit) {
    Column {
        options.forEach { option ->
            // Make the row itself selectable too
            Row(
                Modifier
                    .selectable(selected = (option == selected), onClick = { onOptionSelect(option) }),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                RadioButton(selected = (option == selected), onClick = { onOptionSelect(option) })
                Text(option)
            }
        }
    }
}