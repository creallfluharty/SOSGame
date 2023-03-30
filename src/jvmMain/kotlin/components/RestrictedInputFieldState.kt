package components

class RestrictedInputFieldState<InputType>(
    private val input: InputType,
    private val validator: (InputType) -> Boolean,
) {
    fun getInput() = input
    fun replaceInput(newInput: InputType) =
        RestrictedInputFieldState(if (validator(newInput)) newInput else input, validator)
}