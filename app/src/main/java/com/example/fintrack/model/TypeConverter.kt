package com.example.fintrack.model

import androidx.room.TypeConverter

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
