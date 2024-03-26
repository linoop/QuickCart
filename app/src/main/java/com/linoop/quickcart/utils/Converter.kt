package com.linoop.quickcart.utils

/**
 * Converter is a Generic functional interface to convert ViewModel to either user state or user event
 */
fun interface Converter<I, O> {
    fun convert(input: I): O
}