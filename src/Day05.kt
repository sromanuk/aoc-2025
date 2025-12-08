import kotlin.math.max
import kotlin.math.min

fun main() {
    fun part1(input: List<String>): Int {
        val ranges = mutableListOf<ULongRange>()
        val inputSeparator = input.indexOf("")
        input.subList(0, inputSeparator).forEach { line ->
            line.split("-")
                .map { it.toULong() }
                .let { ranges.add(it.first()..it.last()) }
        }

        var freshnessCounter = 0
        input.subList(inputSeparator + 1, input.size).forEach { line ->
            val id = line.toULong()
            ranges.any { range ->
                if (id in range) {
                    freshnessCounter++
                    true
                } else {
                    false
                }
            }
        }

        return freshnessCounter
    }

    fun part2(input: List<String>): ULong {
        val ranges = mutableListOf<ULongRange>()
        input.subList(0, input.indexOf("")).forEach { line ->
            line.split("-")
                .map { it.toULong() }
                .let {
                    var range = (it.first()..it.last())
                    var intersectionRange: ULongRange?

                    do {
                        intersectionRange = ranges.find {
                            it.contains(range.first) || it.contains(range.last) || range.contains(it.first) || range.contains(it.last)
                        }
                        if (intersectionRange != null) {
                            ranges.remove(intersectionRange)
                            range = min(intersectionRange.first, range.first)..max(intersectionRange.last, range.last)
                        }
                    } while (intersectionRange != null)
                    ranges.add(range)
                }
        }

        return ranges.sumOf {
            it.last - it.first
        }.plus(ranges.size.toULong())
    }

    // Read a large test input from the `src/Day05_test.txt` file:
    val testInput = readInput("Day05_test")
    part2(testInput).println()
    check(part1(testInput) == 3)
    check(part2(testInput) == 14.toULong())

    // Read the input from the `src/Day05.txt` file.
    val input = readInput("Day05")
    part1(input).println()
    part2(input).println()
}
