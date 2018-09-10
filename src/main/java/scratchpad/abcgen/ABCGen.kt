package scratchpad.abcgen

import java.util.*

fun ClosedRange<Int>.random() =
        Random().nextInt((endInclusive + 1) - start) +  start

//val notes = listOf("E,", "F,", "G,", "A,", "B,", "C", "D", "E", "F", "G",
//        "A", "B", "c", "d", "e", "f", "g", "a", "b", "c'", "d'", "e'", "f'", "g'")

val notes = listOf(
        "F", "G", "A", "B", "c", "d", "e", "f", "g")


fun main(args: Array<String>) {
    var currentNote = (0 until notes.size).random()

    print("A4 A4 A4 A4 ")

    for (i in 1..20) {
        val nextSteps = (1..5).random()
        val stepUp = Random().nextBoolean()
        for (j in 1..nextSteps) {
            if (stepUp) {
                if (currentNote+1 < notes.size) {
                    currentNote++
                    print(notes[currentNote] + "4 ")
                } else {
                    break
                }
            } else {
                if (currentNote > 0) {
                    currentNote--
                    print(notes[currentNote] + "4 ")
                } else {
                    break
                }
            }
        }
    }
}
