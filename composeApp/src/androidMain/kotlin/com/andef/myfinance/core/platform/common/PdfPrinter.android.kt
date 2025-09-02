package com.andef.myfinance.core.platform.common

import android.content.Context
import android.graphics.Paint
import android.graphics.Typeface
import android.graphics.pdf.PdfDocument
import android.net.Uri
import android.os.Bundle
import android.os.CancellationSignal
import android.os.ParcelFileDescriptor
import android.print.PageRange
import android.print.PrintAttributes
import android.print.PrintDocumentAdapter
import android.print.PrintDocumentInfo
import android.print.PrintManager
import androidx.core.content.FileProvider
import com.andef.myfinance.core.utils.formatters.datetime.formatLocalDate
import com.andef.myfinance.core.utils.formatters.numbers.formatPriceRuble
import kotlinx.datetime.LocalDate
import java.io.File
import java.io.FileOutputStream

class AndroidPdfPrinter(private val context: Context) : PdfPrinter {
    override fun printIncomePdf(
        incomes: List<Pair<LocalDate, Double>>,
        maxDate: LocalDate,
        minDate: LocalDate
    ) {
        val file = context.generateIncomePdf(incomes, maxDate, minDate)
        context.printPdf(file)
    }


    private fun Context.printPdf(file: File) {
        val uri = FileProvider.getUriForFile(this, "$packageName.provider", file)
        val printManager = getSystemService(Context.PRINT_SERVICE) as PrintManager
        val printAdapter = PdfPrintDocumentAdapter(this, uri)
        printManager.print("Отчет о доходах", printAdapter, null)
    }

    private fun Context.generateIncomePdf(
        incomes: List<Pair<LocalDate, Double>>,
        maxDate: LocalDate,
        minDate: LocalDate
    ): File {
        val pdfDocument = PdfDocument()
        val pageWidth = 595 // A4
        val pageHeight = 842
        val margin = 40f

        val paintText = Paint().apply {
            typeface = Typeface.create(Typeface.SANS_SERIF, Typeface.NORMAL)
            textSize = 12f
            isAntiAlias = true
            color = android.graphics.Color.BLACK
        }
        val paintHeader = Paint().apply {
            typeface = Typeface.create(Typeface.SANS_SERIF, Typeface.BOLD)
            textSize = 16f
            isAntiAlias = true
            color = android.graphics.Color.BLACK
        }
        val paintLine = Paint().apply {
            style = Paint.Style.STROKE
            strokeWidth = 1f
            color = android.graphics.Color.DKGRAY
        }
        val paintFooter = Paint().apply {
            typeface = Typeface.create(Typeface.SANS_SERIF, Typeface.ITALIC)
            textSize = 10f
            isAntiAlias = true
            color = android.graphics.Color.GRAY
        }

        var currentY = margin + 60f
        val rowHeight = 28f
        var pageNumber = 1

        // Задаём координаты колонок
        val columnDateStartX = margin + 4f
        val columnSeparatorX = pageWidth / 2f  // Вертикальная линия посередине страницы
        val columnSumEndX = pageWidth - margin - 4f

        fun startPage(pageNum: Int): PdfDocument.Page {
            currentY = margin + 60f
            val pageInfo = PdfDocument.PageInfo.Builder(pageWidth, pageHeight, pageNumber).create()
            val page = pdfDocument.startPage(pageInfo)
            val canvas = page.canvas

            if (pageNum == 1) {
                // Заголовок по центру — только на первой странице
                paintHeader.textSize = 22f
                val title = "Отчет о доходах"
                val titleWidth = paintHeader.measureText(title)
                canvas.drawText(title, (pageWidth - titleWidth) / 2, margin + 30f, paintHeader)

                // Вторая строка с датами — поменьше шрифт
                paintHeader.textSize = 16f
                val dateRange =
                    "${formatLocalDate(minDate)} - ${formatLocalDate(maxDate)}"
                val dateWidth = paintHeader.measureText(dateRange)
                canvas.drawText(dateRange, (pageWidth - dateWidth) / 2, margin + 55f, paintHeader)
            }

            // Заголовки таблицы — на всех страницах
            paintHeader.textSize = 14f
            val headerY = margin + if (pageNum == 1) 95f else 30f

            canvas.drawText("Дата", columnDateStartX, headerY, paintHeader)
            val sumHeaderText = "Сумма"
            val sumHeaderTextWidth = paintHeader.measureText(sumHeaderText)
            canvas.drawText(sumHeaderText, columnSumEndX - sumHeaderTextWidth, headerY, paintHeader)

            // Горизонтальная линия под заголовками
            val lineY = headerY + 10f
            canvas.drawLine(margin, lineY, pageWidth - margin, lineY, paintLine)

            // Вертикальная линия между столбцами
            canvas.drawLine(
                columnSeparatorX,
                headerY - 20f,
                columnSeparatorX,
                pageHeight - margin,
                paintLine
            )

            currentY = lineY + rowHeight

            return page
        }

        var page = startPage(pageNumber)
        var canvas = page.canvas

        incomes.forEachIndexed { index, (date, totalAmount) ->
            if (currentY + rowHeight > pageHeight - margin - 30f) {
                // Нижний колонтитул с номером страницы
                val pageFooter = "Страница $pageNumber"
                val footerWidth = paintFooter.measureText(pageFooter)
                canvas.drawText(
                    pageFooter,
                    pageWidth - margin - footerWidth,
                    pageHeight - margin / 2,
                    paintFooter
                )

                pdfDocument.finishPage(page)
                pageNumber++
                page = startPage(pageNumber)
                canvas = page.canvas
            }

            // Рисуем дату слева
            canvas.drawText(formatLocalDate(date), columnDateStartX, currentY, paintText)

            // Рисуем сумму справа, выровнено по правому краю колонки
            val sumText = formatPriceRuble(totalAmount)
            val sumTextWidth = paintText.measureText(sumText)
            canvas.drawText(sumText, columnSumEndX - sumTextWidth, currentY, paintText)

            currentY += rowHeight
        }

        // Итого
        val totalSum = incomes.sumOf { it.second }
        if (currentY + rowHeight > pageHeight - margin - 30f) {
            val pageFooter = "Страница $pageNumber"
            val footerWidth = paintFooter.measureText(pageFooter)
            canvas.drawText(
                pageFooter,
                pageWidth - margin - footerWidth,
                pageHeight - margin / 2,
                paintFooter
            )

            pdfDocument.finishPage(page)
            pageNumber++
            page = startPage(pageNumber)
            canvas = page.canvas
        }

        paintHeader.textSize = 14f
        val totalText = "Итого:"
        canvas.drawText(totalText, columnDateStartX, currentY, paintHeader)

        val totalSumText = formatPriceRuble(totalSum)
        val totalSumWidth = paintHeader.measureText(totalSumText)
        canvas.drawText(totalSumText, columnSumEndX - totalSumWidth, currentY, paintHeader)

        // Нижний колонтитул последней страницы
        val pageFooter = "Страница $pageNumber"
        val footerWidth = paintFooter.measureText(pageFooter)
        canvas.drawText(
            pageFooter,
            pageWidth - margin - footerWidth,
            pageHeight - margin / 2,
            paintFooter
        )

        pdfDocument.finishPage(page)

        val file = File(cacheDir, "Отчет_о_доходах.pdf")
        file.outputStream().use { pdfDocument.writeTo(it) }
        pdfDocument.close()
        return file
    }
}

class PdfPrintDocumentAdapter(
    private val context: Context,
    private val uri: Uri
) : PrintDocumentAdapter() {

    override fun onLayout(
        oldAttributes: PrintAttributes?,
        newAttributes: PrintAttributes?,
        cancellationSignal: CancellationSignal?,
        layoutResultCallback: LayoutResultCallback?,
        extras: Bundle?
    ) {
        if (cancellationSignal?.isCanceled == true) {
            layoutResultCallback?.onLayoutCancelled()
            return
        }
        val info = PrintDocumentInfo.Builder("Отчет_о_доходах.pdf")
            .setContentType(PrintDocumentInfo.CONTENT_TYPE_DOCUMENT)
            .build()
        layoutResultCallback?.onLayoutFinished(info, true)
    }

    override fun onWrite(
        pages: Array<out PageRange>?,
        destination: ParcelFileDescriptor?,
        cancellationSignal: CancellationSignal?,
        writeResultCallback: WriteResultCallback?
    ) {
        try {
            context.contentResolver.openInputStream(uri)?.use { input ->
                FileOutputStream(destination?.fileDescriptor).use { output ->
                    input.copyTo(output)
                }
            }
            writeResultCallback?.onWriteFinished(arrayOf(PageRange.ALL_PAGES))
        } catch (e: Exception) {
            writeResultCallback?.onWriteFailed(e.message)
        }
    }
}