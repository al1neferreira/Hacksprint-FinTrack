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
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
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
import com.skydoves.powerspinner.OnSpinnerItemSelectedListener
import com.skydoves.powerspinner.PowerSpinnerView
import java.util.Calendar
import java.util.UUID

class EditExpenseFragment : DialogFragment(R.layout.fragment_edit_expense), MenuProvider {

    private var editExpenseBinding: FragmentEditExpenseBinding? = null
    private val binding get() = editExpenseBinding!!
    private lateinit var colorTransactionEdit: ColorTransaction

    private lateinit var expenseViewModel: ExpenseViewModel
    private lateinit var currentTransaction: Transaction
    private var selectedNewDate: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.FullScreenDialog)
    }

    private fun loadColorSpinnerEditExpense() {
        colorTransactionEdit = ColorList().defaultColorTransaction
        binding.spinnerEditColors.apply {
            adapter = ColorSpinnerAdapter(requireContext(), ColorList().basicColor())
            setSelection(ColorList().colorPosition(colorTransactionEdit), false)
            onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    colorTransactionEdit = ColorList().basicColor()[position]
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

        val dateButton = binding.datePickerEditExpense
        val editTitleExpense: EditText = binding.tvEditTitle
        val editCategories: PowerSpinnerView = binding.spinnerEditCategories
        val editPrice: EditText = binding.newPriceModal
        val btnUpdateExpense: Button = binding.btnUpdateDetail

        editPrice.addTextChangedListener(PriceFormatWatcher(editPrice))



        btnUpdateExpense.setOnClickListener {
            val title = editTitleExpense.text.toString()
            val category = editCategories.text.toString()
            val amount = editPrice.text.toString()
            val color = colorTransactionEdit
            val editId = generateTransactionId()

            val date = "${dateButton.month + 1}/${dateButton.dayOfMonth}/${dateButton.year}"

            val editTransaction = Transaction(title, category, amount, date, color, "", editId)

            expenseViewModel.updateExpense(editTransaction)

        }

        dateButton.setOnClickListener {
            selectedDatePicker()
        }

        loadColorSpinnerEditExpense()
        loadCategorySpinner()

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
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            requireContext(),
            { _, selectedYear, selectedMonth, selectedDayOfMonth ->
                selectedNewDate = "${selectedMonth + 1}/$selectedDayOfMonth/$selectedYear"
                Toast.makeText(
                    requireContext(),
                    "Date selected: $selectedNewDate",
                    Toast.LENGTH_SHORT
                ).show()
            },
            year,
            month,
            day
        )

        datePickerDialog.show()
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


    override fun onDestroyView() {
        super.onDestroyView()
        editExpenseBinding = null
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

    private fun generateTransactionId(): String {
        return UUID.randomUUID().toString()
    }


}