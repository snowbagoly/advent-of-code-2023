fun main() {

    fun part1(input: List<String>): Int {
        val bag = mapOf(
            "red" to 12,
            "green" to 13,
            "blue" to 14
        )
        return input.sumOf { line ->
            val id = Regex("Game (\\d+): ").matchAt(line, 0)!!.groupValues[1].toInt()
            val colors = listOf("red", "green", "blue").associateWith { color ->
                Regex("(\\d+) $color").findAll(line).maxOf { matchResult -> matchResult.groupValues[1].toInt() }
            }
            if (bag.keys.all { color -> colors[color]!! <= bag[color]!! }) id else 0
        }
    }

    fun part2(input: List<String>): Int {
        return input.sumOf { line ->
            listOf("red", "green", "blue").map { color ->
                Regex("(\\d+) $color").findAll(line).maxOf { matchResult -> matchResult.groupValues[1].toInt() }
            }.reduce { acc, element -> acc * element }
        }
    }

    // test if implementation meets criteria from the description, like:
    val testInput1 = readInput("Day02_test1")
    check(part1(testInput1) == 8)
    val testInput2 = readInput("Day02_test2")
    check(part2(testInput2) == 2286)

    val input = readInput("Day02")
    part1(input).println()
    part2(input).println()
}
