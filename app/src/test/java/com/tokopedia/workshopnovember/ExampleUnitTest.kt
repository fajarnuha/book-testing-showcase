package com.tokopedia.workshopnovember

import org.hamcrest.CoreMatchers
import org.junit.Assert.*
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun greeting_isCalled() {
        //given
        val cafe = Cafe()
        cafe.name = "Hey"

        //when
        val actual = cafe.greeting()

        //then
        assertThat(
            actual, CoreMatchers.containsString("Hey")
        )
    }

    @Test
    fun getRemainingCofee() {
        //given
        val cafe = Cafe()
        cafe.coffeeStocks = 7

        //when
        val actual = cafe.remainingCoffee()

        //then
        assertEquals(7, actual)
    }

    @Test
    fun buyCoffee_isCalled() {
        //given
        val cafe = Cafe()
        cafe.coffeeStocks = 7

        //when
        cafe.buyCoffee(5)
        val actual = cafe.remainingCoffee()

        //then
        assertEquals(2, actual)
    }

    @Test
    fun getTotalPrice() {
        //given
        val cafe = Cafe()
        cafe.coffeePrice = 5000

        //when
        val actual = cafe.totalPrice(2)

        //then
        assertEquals(10000, actual)
    }

    @Test
    fun cafe_test() {
        //given
        val cafe = Cafe()
        cafe.apply {
            name = "Hinato"
            coffeeStocks = 3
            coffeePrice = 2500
        }

        //when
        cafe.greeting()
        cafe.remainingCoffee()
        cafe.buyCoffee(2)
        cafe.totalPrice(2)

        //then
        assertEquals("Hi ${cafe.name}, welcome to Cafe", cafe.greeting())
        assertEquals(1, cafe.remainingCoffee())
        assertEquals(5000, cafe.totalPrice(2))
    }

    @Test
    fun isCondition_true() {
        assertTrue(5 > 1)
    }

    @Test
    fun isNull_orNot() {
        val cafe: Cafe? = null
        assertNull(cafe)

        val cafeNot: Cafe = Cafe()
        assertNotNull(cafeNot)
    }

    @Test
    fun isSame() {
        val listof = listOf<Int>(2, 3)
        assertTrue(listof.isNotEmpty())
    }


    class Cafe {
        var name = ""
        var coffeeStocks = 19
        var coffeePrice = 10000

        fun greeting(): String = "Hi $name, welcome to Cafe"

        fun remainingCoffee(): Int = coffeeStocks

        fun buyCoffee(quantity: Int) {
            coffeeStocks -= quantity
        }

        fun totalPrice(quantity: Int): Int = coffeePrice * quantity
    }
}