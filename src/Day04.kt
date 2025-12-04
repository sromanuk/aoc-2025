fun main() {
    fun zeroValue(v: Int): Int = 0

    fun checkCell(matrix: Array<Array<Int>>, i: Int, j: Int): Int {
        val result = (i - 1..i + 1).sumOf { rowIndex ->
            matrix.getOrNull(rowIndex)?.let { column ->
                column.getOrElse(j - 1, ::zeroValue) + column[j] + column.getOrElse(j + 1, ::zeroValue) - if (rowIndex == i) 1 else 0
            } ?: 0
        }
        return if (result < 4) 1 else 0
    }

    fun part1(input: List<String>): Int {
        val matrix = Array(input.size) { Array(input.first().length, ::zeroValue) }
        input.forEachIndexed { i, line -> line.forEachIndexed { j, c -> matrix[i][j] = if (c == '.') 0 else 1 } }

        return (0..<matrix.size).sumOf { i ->
            (0..<matrix.first().size).sumOf { j ->
                if (matrix[i][j] == 0) return@sumOf 0
                checkCell(matrix, i, j)
            }
        }
    }

    fun part2(input: List<String>): Int {
        val matrix = Array(input.size) { Array(input.first().length, ::zeroValue) }
        input.forEachIndexed { i, line -> line.forEachIndexed { j, c -> matrix[i][j] = if (c == '.') 0 else 1 } }

        var result = 0
        do {
            val runResult = (0..<matrix.size).sumOf { i ->
                (0..<matrix.first().size).sumOf { j ->
                    if (matrix[i][j] == 0) return@sumOf 0
                    checkCell(matrix, i, j).also {
                        if (it == 1) matrix[i][j] = 0
                    }
                }
            }
            result += runResult
        } while (runResult > 0)

        return result
    }

    // Read a large test input from the `src/Day04_test.txt` file:
    val testInput = readInput("Day04_test")
    check(part1(testInput) == 13)
    check(part2(testInput) == 43)

    // Read the input from the `src/Day04.txt` file.
    val input = readInput("Day04")
    part1(input).println()
    part2(input).println()
}
