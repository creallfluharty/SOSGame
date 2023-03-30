package components

class ValidatedInputFieldState<InputType, OutputType> private constructor(
    private val input: InputType,
    private val output: OutputType?,
    private val validatorExtractor: (InputType) -> OutputType?,
) {
    constructor(
        input: InputType,
        validatorExtractor: (InputType) -> OutputType?,
    ) : this(input, validatorExtractor(input), validatorExtractor)

    fun isValid() = output != null
    fun getValidValue() = output!!
    fun getInput() = input
    fun replaceInput(newInput: InputType) = ValidatedInputFieldState(newInput, validatorExtractor)
}