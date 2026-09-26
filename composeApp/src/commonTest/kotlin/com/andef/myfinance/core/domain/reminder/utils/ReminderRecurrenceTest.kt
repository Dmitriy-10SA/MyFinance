package com.andef.myfinance.core.domain.reminder.utils

import com.andef.myfinance.core.domain.reminder.entities.ReminderModel
import com.andef.myfinance.core.domain.reminder.entities.ReminderRepeatType
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime
import kotlin.test.Test
import kotlin.test.assertEquals

class ReminderRecurrenceTest {
    @Test
    fun weeklyReminderRepeatsOnSameWeekday() {
        assertOccurrenceDates(
            repeatType = ReminderRepeatType.WEEKLY,
            startDate = LocalDate(2026, 9, 1),
            endDate = LocalDate(2026, 9, 30),
            expected = listOf(
                LocalDate(2026, 9, 1),
                LocalDate(2026, 9, 8),
                LocalDate(2026, 9, 15),
                LocalDate(2026, 9, 22),
                LocalDate(2026, 9, 29)
            )
        )
    }

    @Test
    fun monthlyReminderKeepsAnchorDayAfterShortMonth() {
        assertOccurrenceDates(
            repeatType = ReminderRepeatType.MONTHLY,
            startDate = LocalDate(2024, 1, 1),
            endDate = LocalDate(2024, 3, 31),
            expected = listOf(
                LocalDate(2024, 1, 31),
                LocalDate(2024, 2, 29),
                LocalDate(2024, 3, 31)
            ),
            reminderDate = LocalDate(2024, 1, 31)
        )
    }

    @Test
    fun firstDayReminderKeepsInitialDateAndThenUsesFirstDay() {
        assertOccurrenceDates(
            repeatType = ReminderRepeatType.FIRST_DAY_OF_MONTH,
            startDate = LocalDate(2024, 1, 1),
            endDate = LocalDate(2024, 4, 30),
            expected = listOf(
                LocalDate(2024, 1, 15),
                LocalDate(2024, 2, 1),
                LocalDate(2024, 3, 1),
                LocalDate(2024, 4, 1)
            ),
            reminderDate = LocalDate(2024, 1, 15)
        )
    }

    @Test
    fun firstDayReminderDoesNotDuplicateInitialFirstDay() {
        assertOccurrenceDates(
            repeatType = ReminderRepeatType.FIRST_DAY_OF_MONTH,
            startDate = LocalDate(2024, 1, 1),
            endDate = LocalDate(2024, 3, 31),
            expected = listOf(
                LocalDate(2024, 1, 1),
                LocalDate(2024, 2, 1),
                LocalDate(2024, 3, 1)
            )
        )
    }

    @Test
    fun lastDayReminderUsesActualLastDayOfEveryMonth() {
        assertOccurrenceDates(
            repeatType = ReminderRepeatType.LAST_DAY_OF_MONTH,
            startDate = LocalDate(2024, 1, 1),
            endDate = LocalDate(2024, 4, 30),
            expected = listOf(
                LocalDate(2024, 1, 15),
                LocalDate(2024, 1, 31),
                LocalDate(2024, 2, 29),
                LocalDate(2024, 3, 31),
                LocalDate(2024, 4, 30)
            ),
            reminderDate = LocalDate(2024, 1, 15)
        )
    }

    @Test
    fun lastDayReminderDoesNotDuplicateInitialMonthEnd() {
        assertOccurrenceDates(
            repeatType = ReminderRepeatType.LAST_DAY_OF_MONTH,
            startDate = LocalDate(2024, 1, 1),
            endDate = LocalDate(2024, 2, 29),
            expected = listOf(LocalDate(2024, 1, 31), LocalDate(2024, 2, 29)),
            reminderDate = LocalDate(2024, 1, 31)
        )
    }

    @Test
    fun everyThreeMonthsKeepsAnchorDay() {
        assertOccurrenceDates(
            repeatType = ReminderRepeatType.EVERY_THREE_MONTHS,
            startDate = LocalDate(2024, 1, 1),
            endDate = LocalDate(2024, 10, 31),
            expected = listOf(
                LocalDate(2024, 1, 31),
                LocalDate(2024, 4, 30),
                LocalDate(2024, 7, 31),
                LocalDate(2024, 10, 31)
            ),
            reminderDate = LocalDate(2024, 1, 31)
        )
    }

    @Test
    fun yearlyReminderRestoresLeapDay() {
        assertOccurrenceDates(
            repeatType = ReminderRepeatType.YEARLY,
            startDate = LocalDate(2024, 1, 1),
            endDate = LocalDate(2028, 12, 31),
            expected = listOf(
                LocalDate(2024, 2, 29),
                LocalDate(2025, 2, 28),
                LocalDate(2026, 2, 28),
                LocalDate(2027, 2, 28),
                LocalDate(2028, 2, 29)
            ),
            reminderDate = LocalDate(2024, 2, 29)
        )
    }

    @Test
    fun recurringReminderDoesNotAppearBeforeItsStartDate() {
        assertOccurrenceDates(
            repeatType = ReminderRepeatType.MONTHLY,
            startDate = LocalDate(2026, 4, 1),
            endDate = LocalDate(2026, 6, 30),
            expected = listOf(LocalDate(2026, 5, 15), LocalDate(2026, 6, 15)),
            reminderDate = LocalDate(2026, 5, 15)
        )
    }

    @Test
    fun oneTimeReminderIsReturnedOnlyInsideRange() {
        val reminder = reminder(date = LocalDate(2026, 9, 26), repeatType = null)

        assertEquals(
            listOf(reminder),
            reminder.occurrencesBetween(
                startDate = LocalDate(2026, 9, 20),
                endDate = LocalDate(2026, 9, 30)
            )
        )
        assertEquals(
            emptyList(),
            reminder.occurrencesBetween(
                startDate = LocalDate(2026, 10, 1),
                endDate = LocalDate(2026, 10, 31)
            )
        )
    }

    private fun assertOccurrenceDates(
        repeatType: ReminderRepeatType,
        startDate: LocalDate,
        endDate: LocalDate,
        expected: List<LocalDate>,
        reminderDate: LocalDate = startDate
    ) {
        val occurrences = reminder(reminderDate, repeatType)
            .occurrencesBetween(startDate, endDate)
            .map { it.date }
        assertEquals(expected, occurrences)
    }

    private fun reminder(
        date: LocalDate,
        repeatType: ReminderRepeatType?
    ) = ReminderModel(
        id = 1,
        text = "Оплатить интернет",
        date = date,
        time = LocalTime(18, 0),
        repeatType = repeatType
    )
}
