package components

import androidx.compose.runtime.Composable

@Composable
fun <InputType, OutputType> ValidatedInputField(
    component: @Composable (input: InputType, isValid: Boolean, onValueChange: (InputType) -> Unit) -> Unit,
    state: ValidatedInputFieldState<InputType, OutputType>,
    onValueChange: (InputType) -> Unit,
) {
    component(state.getInput(), state.isValid()) { onValueChange(it) }
}