package com.example.fintrack.model

import androidx.room.Entity
import androidx.room.PrimaryKey

//@Entity
class ColorTransaction(
    //@PrimaryKey
    var name: String,
    var hex: String,
    var constrastHex: String
) {
    val hexHash: String
        get() = "#$hex"
    val constrastHexHash: String
        get() = "#$constrastHex"
}