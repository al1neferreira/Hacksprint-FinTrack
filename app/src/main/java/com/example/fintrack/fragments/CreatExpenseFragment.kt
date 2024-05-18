package com.example.fintrack.fragments


import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.EditText
import android.widget.ImageButton
import androidx.core.view.MenuProvider
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import com.example.fintrack.R
import com.example.fintrack.databinding.FragmentCreatExpenseBinding
import java.util.*

class CreatExpenseFragment : DialogFragment(R.layout.fragment_creat_expense), MenuProvider {

    private var creatExpenseBinding: FragmentCreatExpenseBinding? = null
    private val binding get() = creatExpenseBinding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.FullScreenDialog)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        creatExpenseBinding = FragmentCreatExpenseBinding.inflate(inflater, container, false)

        val dateEditText = binding.edtDateModal
        val pickDateButton = binding.btnPickDate

        pickDateButton.setOnClickListener {
            showDatePicker(dateEditText)
        }

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        dialog?.window?.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        dialog?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
    }

    private fun showDatePicker(editText: EditText) {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            requireContext(),
            { _, selectedYear, selectedMonth, selectedDay ->
                val formattedDate = "$selectedDay/${selectedMonth + 1}/$selectedYear"
                editText.setText(formattedDate)
            },
            year,
            month,
            day
        )
        datePickerDialog.show()
    }

    override fun onDestroyView(){
        super.onDestroyView()
        creatExpenseBinding = null
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        // Implementação do método onCreateMenu
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        // Implementação do método onMenuItemSelected
        return true
    }
}
