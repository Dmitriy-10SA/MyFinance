package com.andef.myfinance.core.di.common

import com.andef.myfinance.core.platform.common.IOSPdfPrinter
import com.andef.myfinance.core.platform.common.PdfPrinter
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

actual fun pdfPrinterModule(): Module = module {
    singleOf(::IOSPdfPrinter).bind<PdfPrinter>()
}