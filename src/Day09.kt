import kotlin.math.abs
import java.awt.geom.Path2D
import java.lang.Double.min

fun main() {
    fun calculateArea(a: Pair<Int, Int>, b: Pair<Int, Int>): Int {
        return (abs(a.first - b.first) + 1) * (abs(a.second - b.second) + 1)
    }
    fun part1(input: List<String>): Int {
        val points = input.map { 
            val coordinates = it.split(",")
            Pair(coordinates[0].toInt(), coordinates[1].toInt())
        }.sortedBy { it.first }
        
        var maxArea = 0
        points.forEach { point ->
            points.reversed().forEach { otherPoint ->
                if (point != otherPoint) {
                    maxArea = maxOf(maxArea, calculateArea(point, otherPoint))
                }
            }
        }
        return maxArea
    }

    fun part2(input: List<String>): Int {
        val points = mutableListOf<Pair<Int, Int>>()
        val path = Path2D.Double().apply {
            input.map { 
                val coordinates = it.split(",")
                points.add(Pair(coordinates[0].toInt(), coordinates[1].toInt()))
                Pair(coordinates[0].toDouble(), coordinates[1].toDouble())
            }.forEachIndexed { index, point ->
                if (index == 0) {
                    moveTo(point.first, point.second)
                } else {
                    lineTo(point.first, point.second)
                }
            }
            closePath()
        }
        
        var maxArea = 0
        points.forEach { point ->
            points.reversed().forEach { otherPoint ->
                if (
                    point != otherPoint && path.contains(
                        min(point.first.toDouble(), otherPoint.first.toDouble()), 
                        min(point.second.toDouble(), otherPoint.second.toDouble()),
                        abs(point.first.toDouble() - otherPoint.first.toDouble()), 
                        abs(point.second.toDouble() - otherPoint.second.toDouble()),
                    )
                ) {
                    maxArea = maxOf(maxArea, calculateArea(point, otherPoint))
                }
            }
        }
        return maxArea
    }

    // Read a large test input from the `src/Day09_test.txt` file:
    val testInput = readInput("Day09_test")
    check(part1(testInput) == 50)
    check(part2(testInput) == 24)

    // Read the input from the `src/Day09.txt` file.
    val input = readInput("Day09")
    part1(input).println()
    part2(input).println()
}
