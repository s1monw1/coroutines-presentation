package de.swirtz.kotlin.coroutines.presentation

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.SendChannel
import kotlinx.coroutines.channels.actor

sealed class CounterMsg {
    object IncCounter : CounterMsg() // one-way message to increment counter
    class GetCounter(val response: CompletableDeferred<Int>) : CounterMsg() // a request with channel for reply.
}

// This function launches a new counter actor
fun CoroutineScope.counterActor() = actor<CounterMsg> {
    var counter = 0 // actor state, not shared
    for (msg in channel) { // handle incoming messages
        when (msg) {
            is CounterMsg.IncCounter -> counter++
            is CounterMsg.GetCounter -> msg.response.complete(counter)
        }
    }
}

suspend fun SendChannel<CounterMsg>.getCurrentCount(): Int {
    val result = CompletableDeferred<Int>()
    send(CounterMsg.GetCounter(result))
    return result.await().also { println("Counter = $it") }
}

fun main(args: Array<String>) = runBlocking<Unit> {
    val counter = counterActor()

    GlobalScope.launch {
        repeat(100) {
            delay(20)
            println("sending IncCounter message")
            counter.send(CounterMsg.IncCounter)
        }
    }

    GlobalScope.launch {
        while (counter.getCurrentCount() < 100) {
            delay(50)
        }
    }.join()
    counter.close() // shutdown the actor
}
