package com.tokopedia.workshopnovember.ui.main;


import junit.framework.Assert.assertEquals
import org.junit.Test

class BasicTest {
    @Test
    fun `is even number`() {
        val number = 7
        val magicNumbers = MagicNumbers()

        val actual = magicNumbers.isEven(number)

        assertEquals(false, actual)
    }

    @Test
    fun `is odd number`() {
        val number = 3
        val magicNumbers = MagicNumbers()

        val actual = magicNumbers.isEven(number)

        assertEquals(true, actual)
    }
}

class MagicNumbers {
    fun isEven(number: Int): Boolean = number % 2 == 0
}


