package com.example.fintrack.fragments

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import java.text.NumberFormat
import java.util.Locale

class PriceFormatWatcher(private val editText: EditText): TextWatcher {

    private var current = ""
    private val locale: Locale = Locale.getDefault()

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

    override fun afterTextChanged(s: Editable?) {
        if(s.toString() != current) {
            editText.removeTextChangedListener(this)

            val cleanString = s.toString().replace("[^\\d]".toRegex(), "")
            val parsed = cleanString.toDoubleOrNull()
            val formatted = if (parsed != null) {
                NumberFormat.getCurrencyInstance(locale).format(parsed / 100)
            } else {
                ""
            }

            current = formatted
            editText.setText(formatted)
            editText.setSelection(formatted.length)

            editText.addTextChangedListener(this)
        }
    }
}