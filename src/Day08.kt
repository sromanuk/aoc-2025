fun main() {
    fun squaredDistance(a: Triple<Int, Int, Int>, b: Triple<Int, Int, Int>): Long {
        val dx = (a.first - b.first).toLong()
        val dy = (a.second - b.second).toLong()
        val dz = (a.third - b.third).toLong()
        return dx * dx + dy * dy + dz * dz
    }

    fun part1(input: List<String>, numberOfPairs: Int = 1000): Int {
        val coordinateList = input.filter { it.isNotBlank() }.map {
            val coordinates = it.split(",")
            Triple(coordinates[0].toInt(), coordinates[1].toInt(), coordinates[2].toInt())
        }

        if (coordinateList.isEmpty()) return 0

        data class Edge(val distance: Long, val a: Int, val b: Int)

        val edges = ArrayList<Edge>()
        for (i in coordinateList.indices) {
            for (j in i + 1 until coordinateList.size) {
                edges.add(
                    Edge(
                        squaredDistance(coordinateList[i], coordinateList[j]),
                        i,
                        j
                    )
                )
            }
        }
        edges.sortBy { it.distance }

        val parent = IntArray(coordinateList.size) { it }
        val size = IntArray(coordinateList.size) { 1 }

        fun find(x: Int): Int {
            if (parent[x] != x) {
                parent[x] = find(parent[x])
            }
            return parent[x]
        }

        fun union(x: Int, y: Int): Boolean {
            var rootX = find(x)
            var rootY = find(y)
            if (rootX == rootY) return false
            if (size[rootX] < size[rootY]) {
                val tmp = rootX
                rootX = rootY
                rootY = tmp
            }
            parent[rootY] = rootX
            size[rootX] += size[rootY]
            return true
        }

        var processed = 0
        val limit = minOf(numberOfPairs, edges.size)
        for (edge in edges) {
            union(edge.a, edge.b)
            processed++
            if (processed == limit) break
        }

        val componentSizes = HashMap<Int, Int>()
        for (i in coordinateList.indices) {
            val root = find(i)
            componentSizes[root] = componentSizes.getOrDefault(root, 0) + 1
        }

        return componentSizes.values
            .sortedDescending()
            .take(3)
            .fold(1) { acc, componentSize -> acc * componentSize }
    }

    fun part2(input: List<String>): ULong {
        val coordinateList = input.filter { it.isNotBlank() }.map {
            val coordinates = it.split(",")
            Triple(coordinates[0].toInt(), coordinates[1].toInt(), coordinates[2].toInt())
        }

        if (coordinateList.size < 2) return 0.toULong()

        data class Edge(val distance: Long, val a: Int, val b: Int)

        val edges = ArrayList<Edge>()
        for (i in coordinateList.indices) {
            for (j in i + 1 until coordinateList.size) {
                edges.add(
                    Edge(
                        squaredDistance(coordinateList[i], coordinateList[j]),
                        i,
                        j
                    )
                )
            }
        }
        edges.sortBy { it.distance }

        val parent = IntArray(coordinateList.size) { it }
        val size = IntArray(coordinateList.size) { 1 }

        fun find(x: Int): Int {
            if (parent[x] != x) parent[x] = find(parent[x])
            return parent[x]
        }

        fun union(x: Int, y: Int): Boolean {
            var rootX = find(x)
            var rootY = find(y)
            if (rootX == rootY) return false
            if (size[rootX] < size[rootY]) {
                val tmp = rootX
                rootX = rootY
                rootY = tmp
            }
            parent[rootY] = rootX
            size[rootX] += size[rootY]
            return true
        }

        var components = coordinateList.size
        var product = 0.toULong()
        for (edge in edges) {
            if (union(edge.a, edge.b)) {
                components--
                if (components == 1) {
                    product = coordinateList[edge.a].first.toULong() * coordinateList[edge.b].first.toULong()
                    break
                }
            }
        }

        return product
    }

    // Read a large test input from the `src/Day08_test.txt` file:
    val testInput = readInput("Day08_test")
    part1(testInput, 10).println()
    check(part1(testInput, 10) == 40)
    check(part2(testInput) == 25272.toULong())

    // Read the input from the `src/Day08.txt` file.
    val input = readInput("Day08")
    part1(input).println()
    part2(input).println()
}
