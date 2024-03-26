package com.linoop.quickcart.utils

fun interface Converter<I, O> {
    fun convert(input: I): O
}