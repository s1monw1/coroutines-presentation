package de.swirtz.kotlin.coroutines.presentation

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import java.math.BigInteger
import java.util.Random
import kotlin.system.measureTimeMillis

fun main() = runBlocking<Unit> {
    log.debug("in runBlocking")
    val time = measureTimeMillis {
        val one = async { doSomethingUsefulOne() }
        val two = async { doSomethingUsefulTwo() }
        println("The answer is ${one.await() + two.await()}")
    }
    println("Completed in $time ms")
}

suspend fun doSomethingUsefulOne(): BigInteger = withContext(Dispatchers.Default) {
    executeAndMeasureTimeMillis {
        log.debug("in doSomethingUsefulOne")
        BigInteger(1500, Random()).nextProbablePrime()
    }
}.also {
    log.debug("Prime calculation took ${it.second} ms")
}.first


suspend fun doSomethingUsefulTwo(): BigInteger = withContext(Dispatchers.Default) {
    executeAndMeasureTimeMillis {
        log.debug("in doSomethingUsefulOne")
        BigInteger(1500, Random()).nextProbablePrime()
    }
}.also {
    log.debug("Prime calculation took ${it.second} ms")
}.first

