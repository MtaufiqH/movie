package com.taufiq.movies.utils

fun Int?.orNol(): Int = this ?: 0
fun Double?.orNol(): Double = this ?: 0.0
fun Float?.orNol(): Float = this ?: 0f
fun Long?.orNol(): Long = this ?: 0L