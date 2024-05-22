package com.example.fintrack.model

import androidx.room.Entity

@Entity
class ColorTransaction(
    var name: String,
    var hex: String,
    constrastHex: String
) {
    val hexHash: String = "#$hex"
    val constrastHexHash: String = "#$constrastHex"
}