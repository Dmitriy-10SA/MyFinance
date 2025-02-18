package com.andef.myfinance.data.formatter

import com.andef.myfinance.domain.entities.Date
import java.text.SimpleDateFormat
import java.util.Locale

object DateFormatterWithSlash {
    fun formatDate(date: Date): String {
        val dateString = "${date.day}/${date.month}/${date.year}"
        val inputFormat = SimpleDateFormat("d/M/yyyy", Locale.getDefault())
        val outputFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        return outputFormat.format(inputFormat.parse(dateString)!!)
    }
}