fun main() {
    fun part1(input: List<String>): Int {
        val steps = input[0]
        val stepMap = input.drop(2)
            .map { line -> line.split(" = ").let { it[0] to it[1].replace("(", "").replace(")", "").split(", ") } }
            .toMap()

        var numberOfSteps = 0
        var pos = "AAA"
        while (pos != "ZZZ") {
            pos = if (steps[numberOfSteps % steps.length] == 'L') stepMap[pos]!![0] else stepMap[pos]!![1]
            ++numberOfSteps
        }

        return numberOfSteps
    }

    fun lnko(x: Long, y: Long): Long {
        var a = x
        var b = y
        while (b != 0L) {
            val m = a % b
            a = b
            b = m
        }
        return a
    }

    fun lkkt(a: Long, b: Long): Long {
        return a * b / lnko(a, b);
    }

    fun calculateSteps(steps: String, stepMap: Map<String, List<String>>, starter: String): Long {
        var numberOfSteps = 0
        var pos = starter
        while (!pos.endsWith('Z')) {
            pos = if (steps[numberOfSteps % steps.length] == 'L') stepMap[pos]!![0] else stepMap[pos]!![1]
            ++numberOfSteps
        }
        return numberOfSteps.toLong()
    }

    fun part2(input: List<String>): Long {
        val steps = input[0]
        val stepMap = input.drop(2)
            .map { line -> line.split(" = ").let { it[0] to it[1].replace("(", "").replace(")", "").split(", ") } }
            .toMap()

        val numberOfSteps = stepMap.keys.filter { key -> key.endsWith('A') }.map { calculateSteps(steps, stepMap, it) }
        return numberOfSteps.reduce(::lkkt)
    }

    // test if implementation meets criteria from the description, like:
    val testInput1 = readInput("Day08_test1")
    check(part1(testInput1) == 2)
    val testInput2 = readInput("Day08_test2")
    check(part1(testInput2) == 6)
    val testInput3 = readInput("Day08_test3")
    check(part2(testInput3) == 6L)

    val input = readInput("Day08")
    part1(input).println()
    part2(input).println()
}
