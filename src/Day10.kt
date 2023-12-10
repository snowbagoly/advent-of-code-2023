fun main() {

    fun neighbors(input: List<String>, coords: Pair<Int, Int>): List<Pair<Int, Int>> {
        val (x, y) = coords
        if (x < 0 || y < 0 || x > input.lastIndex || y > input[0].lastIndex) {
            return listOf()
        }
        return when (input[x][y]) {
            '|' -> listOf(Pair(x - 1, y), Pair(x + 1, y))
            '-' -> listOf(Pair(x, y - 1), Pair(x, y + 1))
            'L' -> listOf(Pair(x - 1, y), Pair(x, y + 1))
            'J' -> listOf(Pair(x, y - 1), Pair(x - 1, y))
            '7' -> listOf(Pair(x, y - 1), Pair(x + 1, y))
            'F' -> listOf(Pair(x + 1, y), Pair(x, y + 1))
            else -> listOf()
        }
    }

    fun part1(input: List<String>): Int {
        val s =
            input.indexOfFirst { it.contains('S') }.let { lineIndex -> Pair(lineIndex, input[lineIndex].indexOf('S')) }
        val sNeighbors = listOf(
            Pair(s.first - 1, s.second),
            Pair(s.first, s.second + 1),
            Pair(s.first + 1, s.second),
            Pair(s.first, s.second - 1)
        ).filter { potentialNeighbor ->
            neighbors(input, potentialNeighbor).contains(s)
        }
        val coordsInPipe = mutableSetOf(s)
        var nextCoords = sNeighbors[0]
        while (true) {
            coordsInPipe.add(nextCoords)
            val potentialNextNeighbor = neighbors(input, nextCoords).filter { !coordsInPipe.contains(it) }
            if (potentialNextNeighbor.isEmpty()) {
                return coordsInPipe.size / 2
            }
            nextCoords = potentialNextNeighbor[0]
        }
    }

    fun areRealCoords(coords: Pair<Double, Double>): Boolean {
        return coords.first % 1.0 < 1e-10 && coords.second % 1.0 < 1e-10
    }

    fun getFakeNeighborsForBfs(
        coordsInPipe: Set<Pair<Double, Double>>,
        coords: Pair<Double, Double>
    ): List<Pair<Double, Double>> {
        return listOf(
            Pair(coords.first + 0.5, coords.second),
            Pair(coords.first, coords.second + 0.5),
            Pair(coords.first - 0.5, coords.second),
            Pair(coords.first, coords.second - 0.5)
        ).filter {
            !coordsInPipe.contains(it)
        }
    }

    fun collectEnclosedCoords(
        from: Pair<Double, Double>,
        input: List<String>,
        coordsInPipe: Set<Pair<Double, Double>>
    ): Int {
        val bfsCoords = mutableListOf(from)
        val enclosedCoords = mutableSetOf(bfsCoords[0])
        while (bfsCoords.isNotEmpty()) {
            val nextBfsCoords = bfsCoords.removeAt(0)
            if (nextBfsCoords.first < 0 || nextBfsCoords.first > input.lastIndex || nextBfsCoords.second < 0 || nextBfsCoords.second > input[0].lastIndex) {
                enclosedCoords.clear()
                bfsCoords.clear()
                break
            }
            for (neighbor in getFakeNeighborsForBfs(coordsInPipe, nextBfsCoords)) {
                if (!enclosedCoords.contains(neighbor)) {
                    bfsCoords.add(neighbor)
                    enclosedCoords.add(neighbor)
                }
            }
        }
        return enclosedCoords.filter { areRealCoords(it) }.size
    }

    fun part2(input: List<String>): Int {
        val s =
            input.indexOfFirst { it.contains('S') }.let { lineIndex -> Pair(lineIndex, input[lineIndex].indexOf('S')) }
        val sNeighbors = listOf(
            Pair(s.first - 1, s.second),
            Pair(s.first, s.second + 1),
            Pair(s.first + 1, s.second),
            Pair(s.first, s.second - 1)
        ).filter { potentialNeighbor ->
            neighbors(input, potentialNeighbor).contains(s)
        }

        // We double the coordinates, so we can do a BFS within the enclosed space
        val coordsInPipe = mutableSetOf(Pair(s.first.toDouble(), s.second.toDouble()))
        var nextPipeCoords = sNeighbors[0]
        while (true) {
            coordsInPipe.add(Pair(nextPipeCoords.first.toDouble(), nextPipeCoords.second.toDouble()))
            val potentialNextNeighbor = neighbors(input, nextPipeCoords).filter {
                !coordsInPipe.contains(
                    Pair(
                        it.first.toDouble(),
                        it.second.toDouble()
                    )
                )
            }
            if (potentialNextNeighbor.isEmpty()) {
                break
            }
            coordsInPipe.add(
                Pair(
                    (potentialNextNeighbor[0].first.toDouble() + nextPipeCoords.first.toDouble()) / 2,
                    (potentialNextNeighbor[0].second.toDouble() + nextPipeCoords.second.toDouble()) / 2
                )
            )
            nextPipeCoords = potentialNextNeighbor[0]
        }
        sNeighbors.forEach {
            coordsInPipe.add(
                Pair(
                    (s.first.toDouble() + it.first.toDouble()) / 2,
                    (s.second.toDouble() + it.second.toDouble()) / 2
                )
            )
        }

        // We try every direction for the BFS and cancel whenever we step outside the pipe map
        val possibleBfsCoords = listOf(
            Pair(s.first - 0.5, s.second + 0.5),
            Pair(s.first - 0.5, s.second - 0.5),
            Pair(s.first + 0.5, s.second + 0.5),
            Pair(s.first + 0.5, s.second - 0.5)
        )
        for (possibleBfsCoord in possibleBfsCoords) {
            val numberOfEnclosedCoords = collectEnclosedCoords(possibleBfsCoord, input, coordsInPipe)
            if (numberOfEnclosedCoords > 0) {
                return numberOfEnclosedCoords
            }
        }
        return 0
    }

    // test if implementation meets criteria from the description, like:
    val testInput1 = readInput("Day10_test1")
    check(part1(testInput1) == 4)
    val testInput2 = readInput("Day10_test2")
    check(part1(testInput2) == 4)
    val testInput3 = readInput("Day10_test3")
    check(part1(testInput3) == 8)

    val testInput4 = readInput("Day10_test4")
    check(part2(testInput4) == 4)
    val testInput5 = readInput("Day10_test5")
    check(part2(testInput5) == 4)
    val testInput6 = readInput("Day10_test6")
    check(part2(testInput6) == 8)
    val testInput7 = readInput("Day10_test7")
    check(part2(testInput7) == 10)


    val input = readInput("Day10")
    part1(input).println()
    part2(input).println()
}
