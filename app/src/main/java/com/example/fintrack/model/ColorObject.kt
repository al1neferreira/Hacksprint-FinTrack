package com.example.fintrack.model

class ColorObject(
    var name: String,
    var hex: String,
    private var constrastHex: String
) {
    val hexHash: String = "#$hex"
    val constrastHexHash: String = "#$constrastHex"
}