package com.tokopedia.workshopnovember.ui.main;

import junit.framework.Assert.assertEquals
import org.junit.Test

class BasicTest {
    @Test
    fun `given is 2 is even number`() {
        val number = 2
        val magicNumbers = MagicNumbers()

        val actual = magicNumbers.isEven(number)

        assertEquals(true, actual)
    }

    @Test
    fun `given is 7 is even number`() {
        val number = 7
        val magicNumbers = MagicNumbers()

        val actual = magicNumbers.isEven(number)

        assertEquals(false, actual)
    }
}

class MagicNumbers {
    fun isEven(number: Int): Boolean = number % 2 == 0
}


