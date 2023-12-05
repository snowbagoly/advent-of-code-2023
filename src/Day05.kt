fun main() {

    data class Transformation(val ranges: List<Triple<Long, Long, Long>>) {
        fun convert(id: Long): Long {
            ranges.forEach { range ->
                if (id >= range.second && id < range.second + range.third) {
                    return id - range.second + range.first
                }
            }
            return id
        }
    }

    fun createTransformation(input: List<String>, currentMap: String, nextMap: String): Transformation {
        val relevantLines =
            input.dropWhile { line -> !line.contains(currentMap) }.takeWhile { line -> !line.contains(nextMap) }.drop(1)
                .dropLast(1)
        return Transformation(relevantLines.map { line ->
            val (dest, source, range) = line.split(" ").map { it.toLong() }
            Triple(dest, source, range)
        })
    }

    fun part1(input: List<String>): Long {
        val seeds = input[0].substring("seeds: ".length).split(" ").map { it.toLong() }.toList()
        val seedToSoil = createTransformation(input, "seed-to-soil map", "soil-to-fertilizer map")
        val soilToFertilizer = createTransformation(input, "soil-to-fertilizer map", "fertilizer-to-water map")
        val fertilizerToWater = createTransformation(input, "fertilizer-to-water map", "water-to-light map")
        val waterToLight = createTransformation(input, "water-to-light map", "light-to-temperature map")
        val lightToTemperature = createTransformation(input, "light-to-temperature map", "temperature-to-humidity map")
        val temperatureToHumidity =
            createTransformation(input, "temperature-to-humidity map", "humidity-to-location map")
        val humidityToLocation = createTransformation(input, "humidity-to-location map", "end")
        return seeds.minOf { seed ->
            humidityToLocation.convert(
                temperatureToHumidity.convert(
                    lightToTemperature.convert(
                        waterToLight.convert(
                            fertilizerToWater.convert(soilToFertilizer.convert(seedToSoil.convert(seed)))
                        )
                    )
                )
            )
        }
    }

    fun part2(input: List<String>): Long {
        val seedRanges = input[0].substring("seeds: ".length).split(" ").map { it.toLong() }.toList()
        val seedToSoil = createTransformation(input, "seed-to-soil map", "soil-to-fertilizer map")
        val soilToFertilizer = createTransformation(input, "soil-to-fertilizer map", "fertilizer-to-water map")
        val fertilizerToWater = createTransformation(input, "fertilizer-to-water map", "water-to-light map")
        val waterToLight = createTransformation(input, "water-to-light map", "light-to-temperature map")
        val lightToTemperature = createTransformation(input, "light-to-temperature map", "temperature-to-humidity map")
        val temperatureToHumidity =
            createTransformation(input, "temperature-to-humidity map", "humidity-to-location map")
        val humidityToLocation = createTransformation(input, "humidity-to-location map", "end")

        var minLocation = Long.MAX_VALUE
        for (i in seedRanges.indices step 2) {
            for (seed in seedRanges[i]..<seedRanges[i] + seedRanges[i + 1]) {
                val location = humidityToLocation.convert(
                    temperatureToHumidity.convert(
                        lightToTemperature.convert(
                            waterToLight.convert(
                                fertilizerToWater.convert(soilToFertilizer.convert(seedToSoil.convert(seed)))
                            )
                        )
                    )
                )
                minLocation = minOf(location, minLocation)
            }
        }
        return minLocation
    }

    // test if implementation meets criteria from the description, like:
    val testInput1 = readInput("Day05_test1")
    check(part1(testInput1) == 35L)
    val testInput2 = readInput("Day05_test2")
    check(part2(testInput2) == 46L)

    val input = readInput("Day05")
    part1(input).println()
    part2(input).println()
}