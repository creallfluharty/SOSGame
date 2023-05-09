package components

// TODO: Just switch back to using a list (of Pair<String, T>) + index
class RadioSelectionState<T>(
    private val options: LinkedHashMap<T, String>,
    private val selected: T
) {
    init {
        if (!options.contains(selected))
            throw IndexOutOfBoundsException("$selected is not an option")
    }

    fun makeSelection(selected: T) = RadioSelectionState(options, selected)

    fun getSelected() = selected

    fun getOptionsIter() = iterator {
        for ((value, label) in options) {
            yield(Triple(label, value, value == selected)) // TODO: ===?
        }
    }
}