package components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.selection.selectable
import androidx.compose.material.RadioButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun <T> RadioSelection(state: RadioSelectionState<T>, onOptionSelect: (T) -> Unit) {
    Column {
        for ((label, value, selected) in state.getOptionsIter()) {
            Row(Modifier
                .selectable(selected = selected, onClick = { onOptionSelect(value) }),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                RadioButton(selected = selected, onClick = { onOptionSelect(value) })
                Text(label)
            }
        }
    }
}