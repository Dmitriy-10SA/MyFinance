package com.andef.myfinance.core.di.reminder

import com.andef.myfinance.core.platform.IosReminderScheduler
import com.andef.myfinance.core.platform.ReminderScheduler
import org.koin.core.module.Module
import org.koin.dsl.bind
import org.koin.dsl.module

actual fun reminderSchedulerModule(): Module = module {
    single { IosReminderScheduler() }.bind<ReminderScheduler>()
}