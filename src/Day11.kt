fun main() {
    fun parseInput(input: List<String>): AdjacencyList {
        val adjacencyListGraph = AdjacencyList()
        
        input.forEach { line ->
            val (source, destinations) = line.split(":")
            destinations.trim().split(" ").forEach { destination ->
                if (destination.isNotBlank()) {
                    adjacencyListGraph.addDirectedEdge(source, destination)
                }
            }
        }
        
        return adjacencyListGraph
    }
    
    fun part1(input: List<String>): ULong {
        val graph = parseInput(input)
        
        return graph.countPaths("you", "out")
    }

    fun part2(input: List<String>): ULong {
        val graph = parseInput(input)

        return graph.countPaths("svr", "fft") *
                graph.countPaths("fft", "dac") *
                graph.countPaths("dac", "out") +
                graph.countPaths("svr", "dac") *
                graph.countPaths("dac", "fft") *
                graph.countPaths("fft", "out")
    }

    // Read a large test input from the `src/Day11_test.txt` file:
    val testInput = readInput("Day11_test")
    part1(testInput).println()
    check(part1(testInput) == 5.toULong())
    val testInput2 = readInput("Day11_part2_test")
    part2(testInput2).println()
    check(part2(testInput2) == 2.toULong())

    // Read the input from the `src/Day11.txt` file.
    val input = readInput("Day11")
    part1(input).println()
    part2(input).println()
}

private class AdjacencyList {
    private val adjacencyMap = mutableMapOf<String, ArrayList<String>>()

    fun addDirectedEdge(source: String, destination: String) {
        if (adjacencyMap[source] == null) {
            adjacencyMap[source] = arrayListOf(destination)
        } else {
            adjacencyMap[source]?.add(destination)
        }
    }

    fun countPaths(source: String, destination: String): ULong {
        val memo = HashMap<String, ULong>()

        fun dfs(current: String): ULong {
            if (current == destination) return 1uL
            if (memo.containsKey(current)) return memo[current]!!

            var count = 0uL
            adjacencyMap[current]?.forEach { neighbor ->
                count += dfs(neighbor)
            }
            memo[current] = count
            return count
        }

        return dfs(source)
    }
}
