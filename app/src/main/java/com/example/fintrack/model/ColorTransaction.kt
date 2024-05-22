package com.example.fintrack.model

class ColorTransaction(
    var name: String,
    var hex: String,
    var constrastHex: String
) {
    val hexHash: String
        get() = "#$hex"
    val constrastHexHash: String
        get() = "#$constrastHex"
}