import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*

fun makeCharsetValidator(validChars: String): (String) -> Boolean {
    val validSet = validChars.toSet()
    return { test -> (test.toSet() - validSet).isEmpty() }
}

fun makeIntRangeValidator(min: Int, max: Int): (String) -> Boolean {
    return { test ->
        try {
            val int = test.toInt()
            int in min until max
        } catch (e: NumberFormatException) {
            false
        }
    }
}

@Composable
fun RestrictedInputField(
    component: @Composable (value: String, onValueChange: (String) -> Unit) -> Unit,
    value: String,
    isAllowed: (String) -> Boolean,
    onValueChange: (String) -> Unit
) {
    component(value) { onValueChange(if (isAllowed(it)) it else value) }
}