fun main() {
    fun part1(input: List<String>): Int {
        var stoppingPoint = 50
        var zeroCounter = 0
        input.forEach { line ->
            val direction = if (line.first() == 'L') -1 else 1
            val distance = line.drop(1).toInt()
            stoppingPoint += (direction * distance) % 100
            if (stoppingPoint < 0) stoppingPoint = 100 + stoppingPoint % 100
            if (stoppingPoint > 99) stoppingPoint %= 100
            if (stoppingPoint == 0) zeroCounter++
        }
        return zeroCounter
    }

    fun part2(input: List<String>): Int {
        var stoppingPoint = 50
        var zeroCounter = 0
        input.forEach { line ->
            val direction = if (line.first() == 'L') -1 else 1
            val distance = line.drop(1).toInt()
            val nextPosition = stoppingPoint + direction * distance
            zeroCounter += if (nextPosition > stoppingPoint) {
                Math.floorDiv(nextPosition, 100) - Math.floorDiv(stoppingPoint, 100)
            } else {
                Math.floorDiv(stoppingPoint - 1, 100) - Math.floorDiv(nextPosition - 1, 100)
            }
            stoppingPoint = nextPosition
            stoppingPoint = Math.floorMod(stoppingPoint, 100)
        }
        return zeroCounter
    }

    // Read a large test input from the `src/Day01_test.txt` file:
    val testInput = readInput("Day01_test")
    check(part1(testInput) == 3)
    check(part2(testInput) == 6)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day01")
    part1(input).println()
    part2(input).println()
}
