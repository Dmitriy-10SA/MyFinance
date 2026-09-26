package com.andef.myfinance

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.andef.myfinance.core.domain.reminder.usecases.GetRemindersAsListUseCase
import com.andef.myfinance.core.platform.reminder.ReminderScheduler
import com.andef.myfinance.core.utils.getters.now
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime
import org.koin.core.context.GlobalContext

class ReminderBootReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if (context == null || intent?.action !in supportedActions) return

        val pendingResult = goAsync()
        CoroutineScope(SupervisorJob() + Dispatchers.IO).launch {
            try {
                val koin = GlobalContext.get()
                val getReminders = koin.get<GetRemindersAsListUseCase>()
                val scheduler = koin.get<ReminderScheduler>()
                val reminders = getReminders(
                    startDate = LocalDate(-9999, 1, 1),
                    endDate = LocalDate(9999, 12, 31)
                )
                val today = LocalDate.now()
                val now = LocalTime.now()

                reminders
                    .filter { reminder ->
                        reminder.repeatType != null || reminder.date > today ||
                            (reminder.date == today && reminder.time > now)
                    }
                    .forEach { reminder ->
                        scheduler.schedule(
                            id = reminder.id,
                            text = reminder.text,
                            date = reminder.date,
                            time = reminder.time,
                            repeatType = reminder.repeatType
                        )
                    }
            } catch (_: Exception) {
                // The next app launch or system event can restore alarms again.
            } finally {
                pendingResult.finish()
            }
        }
    }

    private companion object {
        val supportedActions = setOf(
            Intent.ACTION_BOOT_COMPLETED,
            Intent.ACTION_MY_PACKAGE_REPLACED
        )
    }
}
