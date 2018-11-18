package de.swirtz.kotlin.coroutines.presentation

import org.slf4j.LoggerFactory

val log = LoggerFactory.getLogger("CoroutinesPresentation")

inline fun <R> executeAndMeasureTimeMillis(block: () -> R): Pair<R, Long> {
    val start = System.currentTimeMillis()
    val result = block()
    return result to (System.currentTimeMillis() - start)
}