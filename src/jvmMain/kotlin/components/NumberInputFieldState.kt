package components

fun makeCharsetValidator(validChars: String): (String) -> Boolean {
    val validSet = validChars.toSet()
    return { test -> (test.toSet() - validSet).isEmpty() }
}

fun makeIntRangeValidator(min: Int, max: Int): (String) -> Int? {
    fun validator(test: String): Int? {
        return try {
            val int = test.toInt()
            if (int in min until max) int else null
        } catch (e: NumberFormatException) {
            null
        }
    }

    return ::validator
}

class NumberInputFieldState private constructor(
    private val validatedInputFieldState: ValidatedInputFieldState<RestrictedInputFieldState<String>, Int>,
) {
    private constructor(
        value: Int,
        valueExtractor: (String) -> Int?,
        inputRestriction: (String) -> Boolean
    ) : this(
        ValidatedInputFieldState(
            RestrictedInputFieldState(value.toString(), inputRestriction)
        ) { rInput: RestrictedInputFieldState<String> -> valueExtractor(rInput.getInput()) }
    )
    constructor(value: Int, min: Int, max: Int) : this(
        value,
        makeIntRangeValidator(min, max),
        makeCharsetValidator("0123456789"),
    )

    fun getInput() = validatedInputFieldState.getInput().getInput()

    fun isValid() = validatedInputFieldState.isValid()
    fun getValidValue() = validatedInputFieldState.getValidValue()

    fun replaceInput(input: String) =
        NumberInputFieldState(validatedInputFieldState.replaceInput(validatedInputFieldState.getInput().replaceInput(input)))
}