package com.andef.myfinance.core.data.income_common.income.repository

import com.andef.myfinance.core.data.income_common.income.dao.IncomeDao
import com.andef.myfinance.core.data.income_common.income.mapper.IncomeMapper
import com.andef.myfinance.core.domain.income_common.income.entities.IncomeModel
import com.andef.myfinance.core.domain.income_common.income.repository.IncomeRepository
import com.andef.myfinance.core.domain.income_common.income_category.entities.IncomeCategoryModel
import com.andef.myfinance.core.utils.mappers.localdate.localDateToInt
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.datetime.LocalDate

class IncomeRepositoryImpl(
    private val dao: IncomeDao,
    private val mapper: IncomeMapper
) : IncomeRepository {
    override suspend fun getIncomeById(id: Long): IncomeModel {
        return mapper.toEntity(dao.getIncomeById(id))
    }

    override suspend fun addIncome(incomeModel: IncomeModel) {
        dao.insert(mapper.fromEntity(incomeModel))
    }

    override suspend fun updateIncome(
        id: Long,
        amount: Long,
        category: IncomeCategoryModel,
        date: LocalDate,
        note: String?
    ) {
        dao.update(
            mapper.fromEntity(
                IncomeModel(
                    id = id,
                    amount = amount,
                    category = category,
                    date = date,
                    note = note
                )
            )
        )
    }

    override suspend fun changeAllIncomeCategoryByOldCategory(
        old: String,
        new: String
    ) {
        dao.changeAllIncomeCategoryByOldCategory(old, new)
    }

    override suspend fun deleteAllIncomesByCategory(category: String) {
        dao.deleteAllIncomesByCategory(category)
    }

    override suspend fun deleteIncome(id: Long) {
        dao.deleteById(id)
    }

    override fun getIncomesByDateRangeFlow(
        startDate: LocalDate,
        endDate: LocalDate
    ): Flow<List<IncomeModel>> {
        return dao.getIncomesByDateRangeFlow(localDateToInt(startDate), localDateToInt(endDate))
            .map { dbos -> dbos.map { mapper.toEntity(it) } }
    }

    override suspend fun getIncomesByDateRange(
        startDate: LocalDate,
        endDate: LocalDate
    ): List<IncomeModel> {
        return dao.getIncomesByDateRange(localDateToInt(startDate), localDateToInt(endDate))
            .map { mapper.toEntity(it) }
    }
}
