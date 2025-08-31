package com.andef.myfinance.core.di.reminder

import com.andef.myfinance.core.data.reminder.dao.ReminderDao
import com.andef.myfinance.core.data.reminder.mapper.ReminderMapper
import com.andef.myfinance.core.data.reminder.repository.ReminderRepositoryImpl
import com.andef.myfinance.core.domain.reminder.repository.ReminderRepository
import com.andef.myfinance.core.domain.reminder.usecases.AddReminderUseCase
import com.andef.myfinance.core.domain.reminder.usecases.ChangeReminderUseCase
import com.andef.myfinance.core.domain.reminder.usecases.DeleteReminderUseCase
import com.andef.myfinance.core.domain.reminder.usecases.GetRemindersAsListUseCase
import com.andef.myfinance.core.domain.reminder.usecases.GetRemindersUseCase
import com.andef.myfinance.db.AppDatabase
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

private val reminderRepositoryModule = module {
    singleOf(::ReminderRepositoryImpl).bind<ReminderRepository>()
    factoryOf(::AddReminderUseCase)
    factoryOf(::ChangeReminderUseCase)
    factoryOf(::DeleteReminderUseCase)
    factoryOf(::GetRemindersUseCase)
    factoryOf(::GetRemindersAsListUseCase)
    factoryOf(::GetRemindersUseCase)
}

private val reminderDaoModule = module {
    single { get<AppDatabase>().reminderQueries }
    singleOf(::ReminderDao)
}

private val reminderMapperModule = module {
    singleOf(::ReminderMapper)
}

val reminderModule = listOf(reminderRepositoryModule, reminderDaoModule, reminderMapperModule)