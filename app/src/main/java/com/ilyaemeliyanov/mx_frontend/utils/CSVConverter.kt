package com.ilyaemeliyanov.mx_frontend.utils

import android.content.Context
import com.ilyaemeliyanov.mx_frontend.data.transactions.Transaction
import java.io.File
import java.io.FileOutputStream

object CSVConverter {
    fun List<Transaction>.toCSV(): String {
        val header = "Label,Description,Amount,Date,Wallet"
        val csvRows = this.joinToString("\n") { transaction ->
            "${transaction.label},${transaction.description},${transaction.amount},${transaction.date},${transaction.wallet.name}"
        }
        return "$header\n$csvRows"
    }

    fun exportToCSV(context: Context, fileName: String, data: String): File? {
        return try {
            val file = File(context.getExternalFilesDir(null), "$fileName.csv")
            FileOutputStream(file).use { output ->
                output.write(data.toByteArray())
            }
            file
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}
