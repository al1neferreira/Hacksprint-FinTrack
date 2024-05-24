package com.example.fintrack.model

import java.io.Serializable

class ColorTransaction(
    var name: String,
    var hex: String,
    var constrastHex: String
): Serializable {
    val hexHash: String
        get() = "#$hex"
    val constrastHexHash: String
        get() = "#$constrastHex"
}