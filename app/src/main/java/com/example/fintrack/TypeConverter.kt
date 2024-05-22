import androidx.room.TypeConverter
import com.example.fintrack.model.ColorTransaction

class ColorTransactionConverter {
    @TypeConverter
    fun fromColorTransaction(colorTransaction: ColorTransaction): String {
        return colorTransaction.toString()
    }

    @TypeConverter
    fun toColorTransaction(colorTransactionString: String): ColorTransaction {
        return ColorTransaction(colorTransactionString)
    }
}
