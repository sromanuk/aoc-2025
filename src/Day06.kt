fun main() {
    fun parseInput(input: List<String>): Array<Array<String>> {
        val indexes = mutableListOf<Int>()
        input.last().onEachIndexed { index, char ->
            if (char == '*' || char == '+') indexes.add(index)
        }
        val parsedArray = Array(indexes.size) { Array(input.size) { "" } }
        input.forEachIndexed { i, line ->
            indexes.forEachIndexed { j, operationIndex ->
                val nextIndex = indexes.getOrElse(j + 1) { line.length + 1 }
                parsedArray[j][i] = 
                    line.substring(operationIndex, nextIndex - 1)
            }
        }
        
        return parsedArray
    }
    
    fun part1(input: List<String>): ULong {
        val parsedInput = parseInput(input)
        
        return parsedInput.indices.sumOf { i ->
            val (operation, initial) = parsedInput[i].last().let {
                if (it.trim().first() == '*')
                    (ULong::times to 1.toULong())
                else (ULong::plus to 0.toULong())
            }
            
            parsedInput[i].dropLast(1).fold(initial) { acc, s ->
                val value = s.trim().toUIntOrNull() ?: return@fold acc
                operation(acc, value)
            }
        }
    }

    fun part2(input: List<String>): ULong {
        val parsedInput = parseInput(input)

        return parsedInput.indices.sumOf { i ->
            val (operation, initial) = parsedInput[i].last().let {
                if (it.trim().first() == '*')
                    (ULong::times to 1.toULong())
                else 
                    (ULong::plus to 0.toULong())
            }

            val array = parsedInput[i].dropLast(1).toTypedArray()
            (0..<array[0].length).mapNotNull {
                val builder = StringBuilder()
                for (i in array.indices) {
                    array[i][it].let {
                        if (it != ' ') builder.append(it)
                    }
                }
                builder.toString().toUIntOrNull()
            }.fold(initial) { acc, s ->
                operation(acc, s)
            }
        }
    }

    // Read a large test input from the `src/Day06_test.txt` file:
    val testInput = readInput("Day06_test")
    check(part1(testInput) == 4277556.toULong())
    check(part2(testInput) == 3263827.toULong())

    // Read the input from the `src/Day06.txt` file.
    val input = readInput("Day06")
    part1(input).println()
    part2(input).println()
}
