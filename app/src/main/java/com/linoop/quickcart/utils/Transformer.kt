package com.linoop.quickcart.utils

fun interface Transformer<I, O> {
    fun transform(input: I): O
}