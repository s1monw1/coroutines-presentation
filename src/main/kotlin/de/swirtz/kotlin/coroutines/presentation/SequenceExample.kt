package de.swirtz.kotlin.coroutines.presentation

fun squares(start: Int) = sequence {
    var curr = start
    while (true) {
        println("yielding square of $curr")
        yield(curr * curr)
        curr++
    }
}

fun main(args: Array<String>) {
    for (s in squares(5).take(2)) {
        println("got from sequence: $s")
    }
}
