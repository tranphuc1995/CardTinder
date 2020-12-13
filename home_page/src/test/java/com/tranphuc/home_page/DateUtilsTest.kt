package com.tranphuc.home_page


import com.tranphuc.home_page.utils.DateUtils
import org.junit.Assert.assertEquals
import org.junit.Test



class DateUtilsTest {
    @Test
    fun test_function_format_long_to_date() {
        val input = 790794000000L
        assertEquals("23/01/1995",DateUtils.formatLongToDate(input))
    }
}