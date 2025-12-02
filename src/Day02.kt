fun main() {
    fun part1(input: List<String>): ULong {
        val actualInput = input.first()
        return actualInput
            .split(",")
            .asSequence()
            .map { it.split("-") }
            .map { (a, b) -> a.toULong()..b.toULong() }
            .map { range ->
                val startSize = range.first.toString().length
                val endSize = range.last.toString().length
                if (startSize == endSize && startSize % 2 == 1)
                    return@map emptyList()

                val invalidIDs = mutableListOf<ULong>()
                range.forEach { id ->
                    val idStr = id.toString()
                    val idSize = idStr.length
                    if (idSize % 2 != 0) return@forEach

                    if (idStr.take(idSize / 2).contentEquals(idStr.takeLast(idSize / 2)))
                        invalidIDs.add(id)
                }
                return@map invalidIDs
            }.flatten()
            .sumOf { it }
    }

    fun part2(input: List<String>): ULong {
        val actualInput = input.first()
        return actualInput
            .split(",")
            .asSequence()
            .map { it.split("-") }
            .map { (a, b) -> a.toULong()..b.toULong() }
            .map { range ->
                val startSize = range.first.toString().length
                val endSize = range.last.toString().length

                val invalidIDs = mutableListOf<ULong>()
                range.forEach { id ->
                    val idStr = id.toString()
                    val idSize = idStr.length
                    (1..idSize / 2).forEachIndexed { _, chunkSize ->
                        if (idSize % chunkSize != 0) return@forEachIndexed
                        val replacedString = idStr.replace(idStr.take(chunkSize), "")

                        if (replacedString.isEmpty()) {
                            invalidIDs.add(id)
                            return@forEach
                        }
                    }
                }
                return@map invalidIDs
            }.flatten()
            .sumOf { it }
    }

    // Read a large test input from the `src/Day02_test.txt` file:
    val testInput = readInput("Day02_test")
    check(part1(testInput) == 1227775554.toULong())
    check(part2(testInput) == 4174379265.toULong())

    // Read the input from the `src/Day02.txt` file.
    val input = readInput("Day02")
    part1(input).println()
    part2(input).println()
}
