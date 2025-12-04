fun main() {
    fun part1(input: List<String>): Int {
        return input.sumOf {
            var maxNumber = it.toCharArray().max()
            var maxIndex = it.indexOf(maxNumber)
            if (maxIndex == it.length - 1) {
                maxNumber = it.dropLast(1).toCharArray().max()
                maxIndex = it.indexOf(maxNumber)
            }

            val secondNumber = it.drop(maxIndex + 1).toCharArray().max()
            StringBuilder()
                .apply {
                    append(maxNumber).append(secondNumber)
                }.toString()
                .toInt()
        }
    }

    fun part2(input: List<String>): ULong {
        return input.sumOf {
            var stringBuilder = StringBuilder()

            var tailLengthToDrop = 11
            var headToDrop = 0

            do {
                with(it.drop(headToDrop).dropLast(tailLengthToDrop)) {
                    val number = max()
                    headToDrop += indexOf(number) + 1
                    tailLengthToDrop--
                    stringBuilder = stringBuilder.append(number)
                }
            } while (tailLengthToDrop >= 0)

            val result = stringBuilder.toString()
            if (result.length != 12) 0.toULong() else result.toULong()
        }
    }

    // Read a large test input from the `src/Day03_test.txt` file:
    val testInput = readInput("Day03_test")
    check(part1(testInput) == 357)
    check(part2(testInput) == 3121910778619.toULong())

    // Read the input from the `src/Day03.txt` file.
    val input = readInput("Day03")
    part1(input).println()
    part2(input).println()
}
