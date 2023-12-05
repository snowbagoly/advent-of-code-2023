import kotlin.math.pow

fun main() {

    fun part1(input: List<String>): Int {
        return input.sumOf { line ->
            val (winningNumbers, myNumbers) = line.split(":")[1].split("|").map { Regex("(\\d+)").findAll(it).map { matchResult -> matchResult.groupValues[1].toInt() }.toList() }
            val myWinningNumbers = myNumbers.filter { number -> winningNumbers.contains(number) }
            2f.pow(myWinningNumbers.size - 1).toInt()
        }
    }

    fun part2(input: List<String>): Int {
        val numberOfCards = mutableMapOf<Int,Int>()
        input.forEach { line ->
            val (cardInfo, numberInfo) = line.split(":")
            val cardId = cardInfo.split(" ").last().toInt()
            val (winningNumbers, myNumbers) = numberInfo.split("|").map { Regex("(\\d+)").findAll(it).map { matchResult -> matchResult.groupValues[1].toInt() }.toList() }
            val myWinningNumbers = myNumbers.filter { number -> winningNumbers.contains(number) }

            if (!numberOfCards.containsKey(cardId)) {
                numberOfCards[cardId] = 0
            }
            numberOfCards[cardId] = numberOfCards[cardId]!! + 1
            for (copyCardId in cardId+1..cardId+myWinningNumbers.size) {
                if (!numberOfCards.containsKey(copyCardId)) {
                    numberOfCards[copyCardId] = 0
                }
                numberOfCards[copyCardId] = numberOfCards[copyCardId]!! + numberOfCards[cardId]!!
            }
        }
        return numberOfCards.values.sum()
    }

    // test if implementation meets criteria from the description, like:
    val testInput1 = readInput("Day04_test1")
    check(part1(testInput1) == 13)
    val testInput2 = readInput("Day04_test2")
    check(part2(testInput2) == 30)

    val input = readInput("Day04")
    part1(input).println()
    part2(input).println()
}
