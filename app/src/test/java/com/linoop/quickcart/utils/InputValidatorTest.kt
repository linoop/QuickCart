package com.linoop.quickcart.utils

import com.google.common.truth.Truth
import org.junit.Test

class InputValidatorTest {

    @Test
    fun validateProductId() {
        val validData = InputValidator.validateProductId(1)
        val invalidData = InputValidator.validateProductId(-1)
        Truth.assertThat(validData).isNull()
        Truth.assertThat(invalidData).isNotNull()
    }
}