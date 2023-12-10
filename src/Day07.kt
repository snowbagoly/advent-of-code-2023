fun main() {

    fun calculateTypePoint1(hand: String): Int {
        val cardCounts = hand.toSet().associateWith { card -> hand.count { card == it } }
        if (cardCounts.values.any { it == 5}) {
            return 6
        }
        if (cardCounts.values.any { it == 4}) {
            return 5
        }
        if (cardCounts.values.any { it == 3 } ) {
            if (cardCounts.values.any { it == 2}) {
                return 4
            }
            return 3
        }
        val pairs = cardCounts.keys.filter { cardCounts[it] == 2 }.size
        if (pairs == 2) {
            return 2
        }
        if (pairs == 1) {
            return 1
        }
        return 0
    }

    fun part1(input: List<String>): Int {
        val strength = "23456789TJQKA"
        val valuesToSort = input.map { line ->
            listOf(calculateTypePoint1(line.split(" ")[0])) + line.split(" ")[0].map { strength.indexOf(it) } + listOf(line.split(" ")[1].toInt())
        }

        val sortedValues = valuesToSort.sortedWith (compareBy ({ it[0] }, {it[1]}, {it[2]}, {it[3]}, {it[4]}, {it[5]}))
        return sortedValues.mapIndexed { index, values -> (index + 1) * values[6] }.sum()
    }

    fun calculateTypePoint2(hand: String): Int {
        val cardCounts = hand.toSet().associateWith { card -> hand.count { card == it } }
        val jokers = cardCounts['J'] ?: 0
        val cardCountsOrdered = cardCounts.toList().filter { it.first != 'J' }.map { it.second }.sorted().reversed()

        if ((cardCountsOrdered.getOrNull(0) ?: 0) + jokers == 5 ) {
            return 6
        }
        if ((cardCountsOrdered.getOrNull(0) ?: 0) + jokers == 4 ) {
            return 5
        }
        if ((cardCountsOrdered.getOrNull(0) ?: 0) + jokers == 3 ) {
            if (cardCountsOrdered.drop(1).any { it == 2}) {
                return 4
            }
            return 3
        }
        val pairs = cardCounts.keys.filter { cardCounts[it] == 2 }.size + jokers
        if (pairs == 2) {
            return 2
        }
        if (pairs == 1) {
            return 1
        }
        return 0
    }

    fun part2(input: List<String>): Int {
        val strength = "J23456789TQKA"
        val valuesToSort = input.map { line ->
            listOf(calculateTypePoint2(line.split(" ")[0])) + line.split(" ")[0].map { strength.indexOf(it) } + listOf(line.split(" ")[1].toInt())
        }

        val sortedValues = valuesToSort.sortedWith (compareBy ({ it[0] }, {it[1]}, {it[2]}, {it[3]}, {it[4]}, {it[5]}))
        return sortedValues.mapIndexed { index, values -> (index + 1) * values[6] }.sum()
    }

    // test if implementation meets criteria from the description, like:
    val testInput1 = readInput("Day07_test1")
    check(part1(testInput1) == 6440)
    val testInput2 = readInput("Day07_test2")
    check(part2(testInput2) == 5905)

    val input = readInput("Day07")
    part1(input).println()
    part2(input).println()
}
