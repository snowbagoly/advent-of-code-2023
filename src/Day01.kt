fun main() {
    fun part1(input: List<String>): Int {
        return input.sumOf { line ->
            Regex("\\D*(\\d).*?(\\d)?\\D*").matchEntire(line)!!.groupValues.drop(1).joinToString("").toInt()
                .let { if (it < 10) it * 11 else it }
        }
    }

    fun part2(input: List<String>): Int {
        val digitsAsString = listOf("zero", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine")
        val digitRegex = "(${digitsAsString.joinToString("|")}|\\d)"
        return input.sumOf { line ->
            listOf(".*?$digitRegex.*", ".*$digitRegex.*?").joinToString("") { pattern ->
                Regex(pattern).matchEntire(line)!!.groupValues[1].let {
                    if (digitsAsString.contains(it)) digitsAsString.indexOf(
                        it
                    ).toString() else it
                }
            }.toInt()
        }
    }

    // test if implementation meets criteria from the description, like:
    val testInput1 = readInput("Day01_test1")
    check(part1(testInput1) == 142)
    val testInput2 = readInput("Day01_test2")
    check(part2(testInput2) == 281)

    val input = readInput("Day01")
    part1(input).println()
    part2(input).println()
}
