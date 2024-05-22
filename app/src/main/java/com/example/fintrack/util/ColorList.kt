package com.example.fintrack.util

import com.example.fintrack.model.ColorTransaction

class ColorList {
    private val blackHex = "000000"
    private val whiteHex = "FFFFFF"

    val defaultColorTransaction: ColorTransaction = basicColor()[0]

    fun colorPosition(colorTransaction: ColorTransaction): Int {
        for(i in basicColor().indices){
            if(colorTransaction == basicColor()[i])
                return i
        }
        return 0
    }

    fun basicColor(): List<ColorTransaction>{
        return listOf(
            ColorTransaction("Black", blackHex, whiteHex),
            ColorTransaction("Silver", "C0C0C0", blackHex),
            ColorTransaction("Gray", "808080", whiteHex),
            ColorTransaction("Maroon", "800000", whiteHex),
            ColorTransaction("Red", "FF0000", whiteHex),
            ColorTransaction("Fuchsia", "FF00FF", whiteHex),
            ColorTransaction("Green", "008000", whiteHex),
            ColorTransaction("Lime", "00FF00", blackHex),
            ColorTransaction("Olive", "808000", whiteHex),
            ColorTransaction("Yellow", "FFFF00", blackHex),
            ColorTransaction("Navy", "000080", whiteHex),
            ColorTransaction("Blue", "0000FF", whiteHex),
            ColorTransaction("Teal", "008080", whiteHex),
            ColorTransaction("Aqua", "00FFFF", blackHex)
        )
    }
}