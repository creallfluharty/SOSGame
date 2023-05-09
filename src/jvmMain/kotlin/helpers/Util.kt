package helpers

fun <T> maxByList(list: List<T>, key: (T) -> Int): List<T> {
    val maxItems = mutableListOf<T>()
    var max = 0
    for (item in list)
        key(item).let {
            if (it > max) {
                maxItems.clear()
                maxItems.add(item)
                max = it
            } else if (it == max)
                maxItems.add(item)
        }

    return maxItems
}