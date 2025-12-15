import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.CopyOnWriteArrayList
import java.util.concurrent.CopyOnWriteArraySet
import kotlin.times

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

//        return graph.tracePaths("svr", "fft").size.toULong() * 
//                graph.tracePaths("fft", "dac").size.toULong() *
//                graph.tracePaths("dac", "out").size.toULong() +
//                graph.tracePaths("svr", "dac").size.toULong() *
//                graph.tracePaths("dac", "fft").size.toULong() *
//                graph.tracePaths("fft", "out").size.toULong()
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

private class AdjacencyList{
    private val adjacencyMap = mutableMapOf<String, ArrayList<String>>()
    private val resultList = CopyOnWriteArrayList<List<String>>()
    private val pathsToDestination = ConcurrentHashMap<String, CopyOnWriteArraySet<List<String>>>()

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
    
    private fun findPaths(
        source: String, 
        destination: String, 
        currentList: List<String> = emptyList(),
    ) {
        if (source == destination) {
            resultList.add(listOf(source))
            return
        }
        if (adjacencyMap[source]?.isEmpty() == true) return
        if (currentList.contains(source)) return // Avoid cycles
        
        val path = currentList + listOf(source)
        adjacencyMap[source]?.parallelStream()?.forEach { edge ->
            if (edge == destination) {
                with(path + listOf(edge)) {
                    (0..<size).forEach { 
                        val partialPath = this.drop(it)
                        if (partialPath.isNotEmpty()) {
                            pathsToDestination.getOrDefault(
                                "${partialPath.first()},$destination", 
                                CopyOnWriteArrayList()
                            ).apply {
                                add(partialPath)
                            }
                        }
                    }
                    resultList.add(this)
                }
                return@forEach
            } else if (pathsToDestination["$edge,$destination"] != null) {
                pathsToDestination["$edge,$destination"]?.forEach { resultList.add(currentList + it) }
                return@forEach
            }
            if (adjacencyMap[edge]?.isEmpty() == true) return@forEach

            return@forEach findPaths(edge, destination, path)
        }
    }
    
    fun tracePaths(source: String, destination: String): List<List<String>> {
        resultList.clear()
        findPaths(source, destination)
        return resultList
    }
}
