package components

data class ToggleCarouselState<T>(private val options: List<Pair<String, T>>, private val selectedIndex: Int = 0) {
    init {
        if (selectedIndex >= options.size)
            throw IndexOutOfBoundsException()
    }

    fun getSelectedImageLocation() = options[selectedIndex].first
    fun getSelectedOption() = options[selectedIndex].second

    fun advance() = copy(selectedIndex = (selectedIndex + 1) % options.size)
}