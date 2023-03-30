package components

import androidx.compose.runtime.*

@Composable
fun <InputType> RestrictedInputField(
    state: RestrictedInputFieldState<InputType>,
    onValueChange: (InputType) -> Unit,
    component: @Composable (input: InputType, onValueChange: (InputType) -> Unit) -> Unit,
) {
    component(state.getInput()) { onValueChange(it) }
}