fun main() {

    fun extrapolate(sequence: List<Long>): Long {
        val diff = sequence.indices.drop(1).map { i -> sequence[i] - sequence[i - 1] }
        if (!diff.all { it == 0L }) {
            return sequence.last() + extrapolate(diff)
        }
        return sequence[0]
    }

    fun part1(input: List<String>): Long {
        return input.sumOf { line ->
            val sequence = line.split(" ").map { it.toLong() }
            extrapolate(sequence)
        }
    }

    fun extrapolateBackwards(sequence: List<Long>): Long {
        val diff = sequence.indices.drop(1).map { i -> sequence[i] - sequence[i - 1] }
        if (!diff.all { it == 0L }) {
            return sequence[0] - extrapolateBackwards(diff)
        }
        return sequence[0]
    }

    fun part2(input: List<String>): Long {
        return input.sumOf { line ->
            val sequence = line.split(" ").map { it.toLong() }
            extrapolateBackwards(sequence)
        }
    }

    // test if implementation meets criteria from the description, like:
    val testInput1 = readInput("Day09_test1")
    check(part1(testInput1) == 114L)
    val testInput2 = readInput("Day09_test2")
    check(part2(testInput2) == 2L)

    val input = readInput("Day09")
    part1(input).println()
    part2(input).println()
}
