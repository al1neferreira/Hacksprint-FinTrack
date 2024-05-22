package com.example.fintrack

import androidx.room.TypeConverter
import com.example.fintrack.model.ColorTransaction

class ColorTransactionConverter {
    @TypeConverter
    fun fromColorTransaction(colorTransaction: ColorTransaction): String {
        return "${colorTransaction.name},${colorTransaction.hex},${colorTransaction.constrastHex}"
    }


    @TypeConverter
    fun toColorTransaction(colorTransactionString: String): ColorTransaction {
        val parts = colorTransactionString.split(",")
        return ColorTransaction(parts[0], parts[1], parts[2])
    }
}
