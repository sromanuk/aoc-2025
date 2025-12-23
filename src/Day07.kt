fun main() {
    fun part1(input: List<String>): Int {
        val firstLine = input.first()
        val tachyonState = MutableList(firstLine.length) { 0 }.apply {
            this[firstLine.indexOf("S")] = 1
        }

        var splitCounter = 0
        input.forEach {
            if (!it.contains("^")) return@forEach
            val indexes = mutableListOf<Int>()
            it.forEachIndexed { index, char ->
                if (char == '^') indexes.add(index)
            }

            indexes.forEach { index ->
                if (tachyonState[index] == 1) {
                    splitCounter++
                    tachyonState[index] = 0
                    tachyonState[index - 1] = 1
                    tachyonState[index + 1] = 1
                }
            }
        }

        return splitCounter
    }

    fun part2(input: List<String>): ULong {
        val firstLine = input.first()
        val width = firstLine.length
        val startIndex = firstLine.indexOf("S")

        val tachyonCounts = MutableList(width) { 0uL }.apply {
            this[startIndex] = 1uL
        }

        input.forEach { line ->
            if (!line.contains("^")) return@forEach
            val snapshot = tachyonCounts.toList()
            line.forEachIndexed { index, char ->
                if (char != '^') return@forEachIndexed
                val active = snapshot[index]
                if (active == 0uL) return@forEachIndexed

                tachyonCounts[index] -= active
                if (index > 0) tachyonCounts[index - 1] += active
                if (index < width - 1) tachyonCounts[index + 1] += active
            }
        }

        return tachyonCounts.sum()
    }

    // Read a large test input from the `src/Day07_test.txt` file:
    val testInput = readInput("Day07_test")
    check(part1(testInput) == 21)
    part2(testInput).println()
    check(part2(testInput) == 40.toULong())

    // Read the input from the `src/Day07.txt` file.
    val input = readInput("Day07")
    part1(input).println()
    part2(input).println()
}
