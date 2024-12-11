package utilities;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DateValidationTest {

    @Test
    void isValidDay() {
        assertTrue(DateValidation.isValidDay(31, 1, 2024), "January should have 31 days.");
        assertTrue(DateValidation.isValidDay(30, 4, 2024), "April should have 30 days.");
        assertTrue(DateValidation.isValidDay(29, 2, 2024), "February in a leap year should have 29 days.");
        assertTrue(DateValidation.isValidDay(28, 2, 2023), "February in a non-leap year should have 28 days.");

        assertFalse(DateValidation.isValidDay(0, 1, 2024), "Day 0 is invalid.");
        assertFalse(DateValidation.isValidDay(32, 1, 2024), "Day 32 is invalid.");
        assertFalse(DateValidation.isValidDay(31, 4, 2024), "April does not have 31 days.");
        assertFalse(DateValidation.isValidDay(29, 2, 2023), "February 29 in a non-leap year is invalid.");
    }

    @Test
    void isLeapYear() {
        assertTrue(DateValidation.isLeapYear(2020), "2020 is a leap year.");
        assertTrue(DateValidation.isLeapYear(2000), "2000 is a leap year.");
        assertTrue(DateValidation.isLeapYear(2400), "2400 is a leap year.");

        assertFalse(DateValidation.isLeapYear(2019), "2019 is not a leap year.");
        assertFalse(DateValidation.isLeapYear(1900), "1900 is not a leap year.");
        assertFalse(DateValidation.isLeapYear(2100), "2100 is not a leap year.");
    }

    @Test
    void isValidMonth() {
        for (int month = 1; month <= 12; month++) {
            assertTrue(DateValidation.isValidMonth(month), "Month " + month + " should be valid.");
        }

        assertFalse(DateValidation.isValidMonth(0), "Month 0 is invalid.");
        assertFalse(DateValidation.isValidMonth(13), "Month 13 is invalid.");
        assertFalse(DateValidation.isValidMonth(-5), "Negative months are invalid.");
    }

    @Test
    void isValidYear() {
        assertTrue(DateValidation.isValidYear(1969), "Year 1969 should be valid.");
        assertTrue(DateValidation.isValidYear(2000), "Year 2000 should be valid.");
        assertTrue(DateValidation.isValidYear(2200), "Year 2200 should be valid.");

        assertFalse(DateValidation.isValidYear(1968), "Year 1968 is invalid.");
        assertFalse(DateValidation.isValidYear(2201), "Year 2201 is invalid.");
        assertFalse(DateValidation.isValidYear(1800), "Year 1800 is invalid.");
    }

    @Test
    void isValidDate() {
        assertTrue(DateValidation.isValidDate(15, 6, 2024), "15/06/2024 should be valid.");
        assertTrue(DateValidation.isValidDate(29, 2, 2024), "29/02/2024 should be valid (leap year).");
        assertTrue(DateValidation.isValidDate(28, 2, 2023), "28/02/2023 should be valid (non-leap year).");

        assertFalse(DateValidation.isValidDate(31, 4, 2024), "31/04/2024 should be invalid.");
        assertFalse(DateValidation.isValidDate(29, 2, 2023), "29/02/2023 should be invalid (non-leap year).");
        assertFalse(DateValidation.isValidDate(0, 1, 2024), "00/01/2024 should be invalid.");
        assertFalse(DateValidation.isValidDate(32, 1, 2024), "32/01/2024 should be invalid.");
        assertFalse(DateValidation.isValidDate(15, 13, 2024), "15/13/2024 should be invalid.");
        assertFalse(DateValidation.isValidDate(15, 0, 2024), "15/00/2024 should be invalid.");
        assertFalse(DateValidation.isValidDate(15, 6, 2201), "15/06/2201 should be invalid.");
    }

    @Test
    void formatDate() {
        assertEquals("25/06/2024", DateValidation.formatDate(25062024L), "Formatted date should be 25/06/2024.");
        assertEquals("01/01/2024", DateValidation.formatDate(1012024L), "Formatted date should be 01/01/2024.");
        assertEquals("31/12/2200", DateValidation.formatDate(31122200L), "Formatted date should be 31/12/2200.");
        assertEquals("29/02/2024", DateValidation.formatDate(29022024L), "Formatted date should be 29/02/2024.");
        assertEquals("15/08/1947", DateValidation.formatDate(15081947L), "Formatted date should be 15/08/1947.");
    }

    @Test
    void getTodayAsLong() {
        long todayAsLong = DateValidation.getTodayAsLong();
        String expectedDate = java.time.LocalDate.now().format(java.time.format.DateTimeFormatter.ofPattern("ddMMyyyy"));
        assertEquals(Long.parseLong(expectedDate), todayAsLong, "Today's date should be correctly converted to long.");
    }

    @Test
    void compareDates() {
        assertEquals(-1, DateValidation.compareDates(1012024L, 2012024L), "01/01/2024 is before 02/01/2024.");
        assertEquals(-1, DateValidation.compareDates(31122023L, 1012024L), "31/12/2023 is before 01/01/2024.");

        assertEquals(0, DateValidation.compareDates(1012024L, 1012024L), "Dates are the same.");
        assertEquals(0, DateValidation.compareDates(29022024L, 29022024L), "Dates are the same.");

        assertEquals(1, DateValidation.compareDates(2012024L, 1012024L), "02/01/2024 is after 01/01/2024.");
        assertEquals(1, DateValidation.compareDates(1012025L, 31122024L), "01/01/2025 is after 31/12/2024.");
    }
}
