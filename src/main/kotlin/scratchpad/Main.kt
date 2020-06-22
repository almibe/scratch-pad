@file:JvmName("Main")

package scratchpad

import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking
import java.lang.Exception
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.util.stream.Collectors

fun main(args: Array<String>) {
    val start = System.currentTimeMillis()
    if (args.size == 1 && Files.isDirectory(Paths.get(args[0]))) {
        runBlocking {
            createReport(Paths.get(args[0]))
        }
    } else {
        error("Enter valid directory name.")
    }
    val end = System.currentTimeMillis()
    println("***${end - start}")
}

suspend fun createReport(path: Path) {
    val files = getFiles(path)
    val result: Set<Pair<Path, Int>> = files.map {file ->
        val lines = getLines(file)
        val indentations = lines.map { getChars(it) }.map { getIndentation(it) }
        val max: Int = indentations.fold(0) { max, current -> if(max > current) max else current }
        file to max
    }.toSet()

    result.sortedBy { it.second }.forEach {
        println("${it.first.toAbsolutePath()} - ${it.second}")
    }
}

fun getFiles(path: Path): Flow<Path> =
    Files.walk(path).filter { Files.isRegularFile(it) }.collect(Collectors.toList()).asFlow()

fun getLines(file: Path): Flow<String> {
    return try {
        Files.readAllLines(file).asFlow()
    } catch (ex: Exception) {
        flow {}
    }
}

fun getChars(line: String): Flow<Char> =
    line.toCharArray().toList().asFlow()

suspend fun getIndentation(chars: Flow<Char>): Int =
    chars.takeWhile { it == ' ' }.count()
