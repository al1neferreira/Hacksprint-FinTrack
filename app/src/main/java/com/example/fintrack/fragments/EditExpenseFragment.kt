package com.example.fintrack.fragments

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.AdapterView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.core.view.MenuProvider
import androidx.fragment.app.DialogFragment
import com.example.fintrack.R
import com.example.fintrack.adapter.ColorSpinnerAdapter
import com.example.fintrack.databinding.FragmentEditExpenseBinding
import com.example.fintrack.model.ColorTransaction
import com.example.fintrack.model.Transaction
import com.example.fintrack.util.ColorList
import com.example.fintrack.viewModel.ExpenseViewModel
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.MaterialDatePicker
import com.skydoves.powerspinner.OnSpinnerItemSelectedListener
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import java.util.TimeZone

class EditExpenseFragment : DialogFragment(R.layout.fragment_edit_expense), MenuProvider {

    private var editExpenseBinding: FragmentEditExpenseBinding? = null
    private val binding get() = editExpenseBinding!!
    private lateinit var selectedColor: ColorTransaction

    private lateinit var expenseViewModel: ExpenseViewModel
    private lateinit var currentTransaction: Transaction
    private val calendar: Calendar = Calendar.getInstance()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.FullScreenDialog)
    }

    private fun loadColorSpinnerEditExpense() {
        selectedColor = ColorList().defaultColorTransaction
        binding.spinnerEditColors.apply {
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

    private fun loadCategorySpinner() {
        val categories = listOf(
            "Food",
            "Transport",
            "Entertainment",
            "Health",
            "Internet",
            "Home",
            "Clothe",
            "Electricity",
            "Gas station",
            "Game control",
            "Others"
        )
        binding.spinnerEditCategories.apply {
            setItems(categories)
            setOnSpinnerItemSelectedListener(OnSpinnerItemSelectedListener<String> { oldIndex, oldItem, newIndex, newItem ->
                Toast.makeText(requireContext(), "$newItem selected!", Toast.LENGTH_SHORT).show()
            })
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
        loadCategorySpinner()

        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.datePickerEditExpense.setOnDateChangedListener { _, year, monthOfYear, dayOfMonth ->
            calendar.set(Calendar.YEAR, year)
            calendar.set(Calendar.MONTH, monthOfYear)
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
        }

        binding.btnUpdateDetail.setOnClickListener {
            val editExpenseTitle = binding.tvEditTitle.text.toString()
            val editCategory = binding.spinnerEditCategories.toString()
            val editColor = binding.spinnerEditColors.toString()

        }

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

    private fun deleteExpense() {
        AlertDialog.Builder(requireActivity()).apply {
            setTitle("Delete Expense")
            setMessage("Do you want to delete this expense?")
            setPositiveButton("Delete") { _, _ ->
                expenseViewModel.deleteExpense(currentTransaction)
                Toast.makeText(context, "Expense Deleted", Toast.LENGTH_SHORT).show()

            }
            setNegativeButton("Cancel", null)
        }.create().show()

    }


    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menu.clear()
        menuInflater.inflate(R.menu.menu_detail, menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return when (menuItem.itemId) {
            R.id.menu_delete -> {
                deleteExpense()
                true
            }

            else -> false
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        editExpenseBinding = null
    }

}