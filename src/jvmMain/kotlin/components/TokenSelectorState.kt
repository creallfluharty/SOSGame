package components

// TODO maybe combine this with RadioSelectionState?
class TokenSelectorState(
    private val tokens: List<Token>,
    private val selectedIndex: Int,
) {
    init {
        if (selectedIndex !in tokens.indices)
            throw IndexOutOfBoundsException("No tokens there >:(")
    }

    fun setSelectedIndex(index: Int) = TokenSelectorState(tokens, index)

    fun getSelection() = tokens[selectedIndex]

    fun getNumTokens() = tokens.size

    fun getOptionIter() = iterator {
        tokens.forEachIndexed { index, token ->
            yield(Triple(index, token, index == selectedIndex)) // TODO: withIndexed
        }
    }
}