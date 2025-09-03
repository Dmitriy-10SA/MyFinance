package com.andef.myfinance.core.platform.common

import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.datetime.LocalDate
import platform.CoreGraphics.CGContextAddLineToPoint
import platform.CoreGraphics.CGContextMoveToPoint
import platform.CoreGraphics.CGContextSetLineWidth
import platform.CoreGraphics.CGContextSetStrokeColorWithColor
import platform.CoreGraphics.CGContextStrokePath
import platform.CoreGraphics.CGPointMake
import platform.CoreGraphics.CGRectMake
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSString
import platform.Foundation.NSURL
import platform.Foundation.NSUserDomainMask
import platform.Foundation.writeToURL
import platform.UIKit.NSFontAttributeName
import platform.UIKit.NSForegroundColorAttributeName
import platform.UIKit.UIColor
import platform.UIKit.UIFont
import platform.UIKit.UIGraphicsGetCurrentContext
import platform.UIKit.UIGraphicsPDFRenderer
import platform.UIKit.UIGraphicsPDFRendererFormat
import platform.UIKit.UIPrintInfo
import platform.UIKit.UIPrintInfoOutputType
import platform.UIKit.UIPrintInteractionController
import platform.UIKit.drawAtPoint

class IOSPdfPrinter() : PdfPrinter {
    override fun printIncomePdf(
        incomes: List<Pair<LocalDate, Double>>,
        maxDate: LocalDate,
        minDate: LocalDate
    ) {
        print(true, incomes, maxDate, minDate)
    }

    override fun printExpensePdf(
        expenses: List<Pair<LocalDate, Double>>,
        maxDate: LocalDate,
        minDate: LocalDate
    ) {
        print(false, expenses, maxDate, minDate)
    }

    @OptIn(ExperimentalForeignApi::class)
    private fun print(
        isIncome: Boolean,
        amounts: List<Pair<LocalDate, Double>>,
        maxDate: LocalDate,
        minDate: LocalDate
    ) {
        val fileName = if (isIncome) "Отчет_о_доходах.pdf" else "Отчет_о_расходах.pdf"
        val fileManager = NSFileManager.defaultManager
        val urls = fileManager.URLsForDirectory(NSDocumentDirectory, NSUserDomainMask)
        val docsDir = urls.first() as? NSURL
            ?: throw IllegalStateException("Не удалось получить Documents directory")
        val fileURL = docsDir.URLByAppendingPathComponent(fileName)

        // Формат страницы A4
        val pageWidth = 595.0
        val pageHeight = 842.0
        val margin = 40.0
        val rowHeight = 28.0

        val format = UIGraphicsPDFRendererFormat()
        val pageBounds = CGRectMake(0.0, 0.0, pageWidth, pageHeight)
        val renderer = UIGraphicsPDFRenderer(bounds = pageBounds, format = format)

        val data = renderer.PDFDataWithActions { ctx ->
            ctx?.beginPage()

            // Атрибуты текста
            val attrsTitle: Map<Any?, *> = mapOf(
                NSFontAttributeName to UIFont.boldSystemFontOfSize(22.0)
            )
            val attrsHeader: Map<Any?, *> = mapOf(
                NSFontAttributeName to UIFont.boldSystemFontOfSize(14.0)
            )
            val attrsText: Map<Any?, *> = mapOf(
                NSFontAttributeName to UIFont.systemFontOfSize(12.0)
            )
            val attrsFooter: Map<Any?, *> = mapOf(
                NSFontAttributeName to UIFont.italicSystemFontOfSize(10.0),
                NSForegroundColorAttributeName to UIColor.grayColor
            )

            // Заголовок
            val title = if (isIncome) "Отчет о доходах" else "Отчет о расходах"
            (title as NSString).drawAtPoint(
                point = CGPointMake((pageWidth - 200.0) / 2, margin),
                withAttributes = attrsTitle
            )

            // Дата диапазона
            val dateRange = "${minDate.toString()} - ${maxDate.toString()}"
            (dateRange as NSString).drawAtPoint(
                point = CGPointMake((pageWidth - 200.0) / 2, margin + 30.0),
                withAttributes = attrsHeader
            )

            // Заголовки таблицы
            val headerY = margin + 70.0
            ("Дата" as NSString).drawAtPoint(
                point = CGPointMake(margin, headerY),
                withAttributes = attrsHeader
            )
            ("Сумма" as NSString).drawAtPoint(
                point = CGPointMake(pageWidth - margin - 100.0, headerY),
                withAttributes = attrsHeader
            )

            // Горизонтальная линия под заголовками
            val ctxRef = UIGraphicsGetCurrentContext()
            CGContextSetStrokeColorWithColor(ctxRef, UIColor.darkGrayColor.CGColor)
            CGContextSetLineWidth(ctxRef, 1.0)
            CGContextMoveToPoint(ctxRef, margin, headerY + 15.0)
            CGContextAddLineToPoint(ctxRef, pageWidth - margin, headerY + 15.0)
            CGContextStrokePath(ctxRef)

            // Таблица
            var currentY = headerY + rowHeight
            amounts.forEach { (date, sum) ->
                (date.toString() as NSString).drawAtPoint(
                    point = CGPointMake(margin, currentY),
                    withAttributes = attrsText
                )
                (sum.toString() as NSString).drawAtPoint(
                    point = CGPointMake(pageWidth - margin - 100.0, currentY),
                    withAttributes = attrsText
                )
                currentY += rowHeight
            }

            // Итого
            val total = amounts.sumOf { it.second }
            ("Итого:" as NSString).drawAtPoint(
                point = CGPointMake(margin, currentY + 10.0),
                withAttributes = attrsHeader
            )
            (total.toString() as NSString).drawAtPoint(
                point = CGPointMake(pageWidth - margin - 100.0, currentY + 10.0),
                withAttributes = attrsHeader
            )

            // Нижний колонтитул
            val footerText = "Страница 1"
            (footerText as NSString).drawAtPoint(
                point = CGPointMake(pageWidth - margin - 60.0, pageHeight - margin / 2),
                withAttributes = attrsFooter
            )
        }

        // Сохраняем PDF
        data.writeToURL(fileURL!!, atomically = true)

        // Отправляем в печать
        val printController = UIPrintInteractionController.sharedPrintController()
        val printInfo = UIPrintInfo.printInfo()
        printInfo.outputType = UIPrintInfoOutputType.UIPrintInfoOutputGeneral
        printInfo.jobName = fileName
        printController.printInfo = printInfo
        printController.printingItem = fileURL

        printController.presentAnimated(true, completionHandler = null)
    }
}