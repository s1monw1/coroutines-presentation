package de.swirtz.kotlin.coroutines.presentation

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main(args: Array<String>) = runBlocking<Unit> {
    val sharedChannel = Channel<String>()
    launch {
        repeat(3) {
            sharedChannel.send("element_$it")
        }
        sharedChannel.close()
    }

    launch {
        for (element in sharedChannel) {
            println("received from channel: $element")
        }
    }
}