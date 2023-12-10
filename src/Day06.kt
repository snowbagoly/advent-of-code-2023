import kotlin.math.ceil
import kotlin.math.floor
import kotlin.math.pow

fun main() {
    fun calculateNumberOfWays(time: Long, distance: Long): Long {
        // We need the roots of this rounded to Long: - pressedSec ** 2 + time * pressedSec - distance > 0
        // We only have a solution if discriminant is non-negative
        val discriminant = time.toDouble().pow(2) - 4 * distance.toDouble()
        if (discriminant < 0) {
            return 0
        }

        // We adjust the roots by 0.00001 to exclude equal case
        val root1 = ceil((-time + discriminant.pow(0.5)) / (-2) + 0.00001)
        val root2 = floor((-time - discriminant.pow(0.5)) / (-2) - 0.00001)
        return (root2 - root1 + 1).toLong()
    }

    fun part1(input: List<String>): Long {
        val times = input[0].split(Regex("\\D+")).drop(1).map { it.toLong() }
        val distances = input[1].split(Regex("\\D+")).drop(1).map { it.toLong() }

        return times.indices.map { i ->
            calculateNumberOfWays(times[i], distances[i])
        }.reduce { acc, element -> acc * element }
    }

    fun part2(input: List<String>): Long {
        val time = input[0].replace(Regex("\\D"), "").toLong()
        val distance = input[1].replace(Regex("\\D"), "").toLong()
        return calculateNumberOfWays(time, distance)
    }

    // test if implementation meets criteria from the description, like:
    val testInput1 = readInput("Day06_test1")
    check(part1(testInput1) == 288L)
    val testInput2 = readInput("Day06_test2")
    check(part2(testInput2) == 71503L)

    val input = readInput("Day06")
    part1(input).println()
    part2(input).println()
}
