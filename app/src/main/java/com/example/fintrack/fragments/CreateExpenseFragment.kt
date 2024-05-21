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
import android.widget.AdapterView
import android.widget.EditText
import androidx.core.view.MenuProvider
import androidx.fragment.app.DialogFragment
import com.example.fintrack.util.ColorList
import com.example.fintrack.model.ColorObject
import com.example.fintrack.adapter.ColorSpinnerAdapter
import com.example.fintrack.R
import com.example.fintrack.databinding.FragmentCreatExpenseBinding
import java.util.Calendar

class CreateExpenseFragment : DialogFragment(R.layout.fragment_creat_expense), MenuProvider {

    private var creatExpenseBinding: FragmentCreatExpenseBinding? = null
    private val binding get() = creatExpenseBinding!!
    private lateinit var selectedColor: ColorObject

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.FullScreenDialog)

    }

    private fun loadColorSpinner() {
        selectedColor = ColorList().defaultColor
        binding.spinnerColors.apply {
            adapter = ColorSpinnerAdapter(requireContext(), ColorList().basicColor())
            setSelection(ColorList().colorPosition(selectedColor), false)
            onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    selectedColor = ColorList().basicColor()[position]
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {}
            }
        }


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        creatExpenseBinding = FragmentCreatExpenseBinding.inflate(inflater, container, false)

        val dateEditText = binding.edtDateModal
        val pickDateButton = binding.btnPickDate

        pickDateButton.setOnClickListener {
            showDatePicker(dateEditText)
        }

        loadColorSpinner()
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
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

    override fun onDestroyView() {
        super.onDestroyView()
        creatExpenseBinding = null
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        TODO()
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        TODO()
    }
}
