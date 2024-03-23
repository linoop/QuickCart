package com.linoop.quickcart.utils

import com.linoop.quickcart.R

object InputValidator {

    /**
     * Query validator
     * @param input [String]
     * @return if the input password is not valid returns error code as a String resource ID
     */
    fun validateProductId(input: Int?): Int? {
        return when {
            input == null -> R.string.app_name
            input < 0 -> R.string.app_name
            else -> null
        }
    }
}