package com.example.fintrack.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.AdapterView
import androidx.core.view.MenuProvider
import androidx.fragment.app.DialogFragment
import com.example.fintrack.R
import com.example.fintrack.adapter.ColorSpinnerAdapter
import com.example.fintrack.databinding.FragmentEditExpenseBinding
import com.example.fintrack.model.ColorObject
import com.example.fintrack.util.ColorList
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.MaterialDatePicker
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import java.util.TimeZone

class EditExpenseFragment : DialogFragment(R.layout.fragment_edit_expense), MenuProvider {

    private var editExpenseBinding: FragmentEditExpenseBinding? = null
    private val binding get() = editExpenseBinding!!
    private lateinit var selectedColor: ColorObject

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.FullScreenDialog)
    }

    private fun loadColorSpinnerEditExpense() {
        selectedColor = ColorList().defaultColor
        binding.spinnerColors.apply {
            adapter = ColorSpinnerAdapter(requireContext(), ColorList().basicColor())
            setSelection(ColorList().colorPosition(selectedColor), false)
            onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    selectedColor = ColorList().basicColor()[position]
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {}
            }
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        editExpenseBinding = FragmentEditExpenseBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment

        selectedDatePicker()
        loadColorSpinnerEditExpense()

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

    private fun selectedDatePicker() {
        val editDate = MaterialDatePicker.Builder.datePicker()
            .setTitleText("Select date of expense")
            .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
            .build()

        val today = MaterialDatePicker.todayInUtcMilliseconds()
        val calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"))

        calendar.timeInMillis = today
        calendar[Calendar.MONTH] = Calendar.JANUARY
        val janThisYear = calendar.timeInMillis

        calendar.timeInMillis = today
        calendar[Calendar.MONTH] = Calendar.DECEMBER
        val decThisYear = calendar.timeInMillis

        val constraintsBuilder =
            CalendarConstraints.Builder()
                .setStart(janThisYear)
                .setEnd(decThisYear)

        editDate.addOnPositiveButtonClickListener {
            val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            val date = sdf.format(it)

            //editDate.show(requireFragmentManager(), "Date Picker")
        }


    }
    override fun onDestroyView() {
        super.onDestroyView()
        editExpenseBinding = null
    }


    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        TODO("Not yet implemented")
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        TODO("Not yet implemented")
    }

}