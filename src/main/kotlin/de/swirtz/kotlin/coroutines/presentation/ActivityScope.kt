package de.swirtz.kotlin.coroutines.presentation

import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class Activity : CoroutineScope {
    lateinit var job: Job //tied to lifecycle of Activity
    fun create() {
        job = Job()
    }

    fun destroy() {
        //will cancel all child jobs as well
        println("cancel $job and all ${job.children.toList().size} children")
        job.cancel()
    }

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Default + job + CoroutineName("MyActivityContext")

    fun doSomething() {
        repeat(10) { i ->
            //structured concurrency, we launch in the outer scope of Activity
            // with [coroutineContext]
            launch {
                delay((i + 1) * 200L)
                println("Coroutine $i is done")
            }
        }
    }
}


fun main(args: Array<String>) = runBlocking {
    with(Activity()) {
        create()
        doSomething()
        println("Launched coroutines")
        delay(500L)
        println("Destroying activity!")
        destroy()
        delay(1000L)
    }

}
