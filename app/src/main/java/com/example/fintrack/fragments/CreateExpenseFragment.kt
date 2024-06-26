package com.example.fintrack.fragments

import android.app.DatePickerDialog
import android.content.Context
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
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.view.MenuProvider
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.room.Room
import com.example.fintrack.R
import com.example.fintrack.adapter.ColorSpinnerAdapter
import com.example.fintrack.databinding.FragmentCreatExpenseBinding
import com.example.fintrack.db.ExpenseDao
import com.example.fintrack.db.ExpenseDatabase
import com.example.fintrack.home.HomeActivity
import com.example.fintrack.home.HomeViewModel
import com.example.fintrack.model.ColorTransaction
import com.example.fintrack.model.Transaction
import com.example.fintrack.util.ColorList
import com.skydoves.powerspinner.OnSpinnerItemSelectedListener
import com.skydoves.powerspinner.PowerSpinnerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Calendar

class CreateExpenseFragment : DialogFragment(R.layout.fragment_creat_expense), MenuProvider {

    private var creatExpenseBinding: FragmentCreatExpenseBinding? = null
    private val binding get() = creatExpenseBinding!!
    private lateinit var selectedColorTransaction: ColorTransaction
    private val homeViewModel: HomeViewModel by activityViewModels()
    private var selectedDate: String? = null
    private lateinit var applicationContext: Context
    private lateinit var expenseDao: ExpenseDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.FullScreenDialog)

        applicationContext = requireContext()
        expenseDao = db.getExpenseDao()
    }

    private val db by lazy {
        Room.databaseBuilder(
            applicationContext,
            ExpenseDatabase::class.java, "database-expense"
        ).build()
    }

    private fun loadColorSpinner() {
        selectedColorTransaction = ColorList().defaultColorTransaction
        binding.spinnerColors.apply {
            adapter = ColorSpinnerAdapter(requireContext(), ColorList().basicColor())
            setSelection(ColorList().colorPosition(selectedColorTransaction), false)
            onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    selectedColorTransaction = ColorList().basicColor()[position]
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
        selectCategory(categories)
    }

    private fun selectCategory(categories: List<String>) {
        binding.psvCategory.apply {
            setItems(categories)
            setOnSpinnerItemSelectedListener(OnSpinnerItemSelectedListener<String> { oldIndex, oldItem, newIndex, newItem ->
                Toast.makeText(requireContext(), "$newItem selected!", Toast.LENGTH_SHORT).show()
            })
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        creatExpenseBinding = FragmentCreatExpenseBinding.inflate(inflater, container, false)

        val pickDateButton = binding.btnPickDate
        val backButton: ImageView = binding.btnBack
        val edtTitleModal: EditText = binding.edtTitleModal
        val psvCategory: PowerSpinnerView = binding.psvCategory
        val edtPriceModal: EditText = binding.edtPriceModal
        val btnCreateExpense: Button = binding.btnCreateExpense

        edtPriceModal.addTextChangedListener(PriceFormatWatcher(edtPriceModal))

        selectDate(pickDateButton)

        createANewExpense(btnCreateExpense, edtTitleModal, psvCategory, edtPriceModal, pickDateButton)

        backToHomeActivity(backButton)

        loadColorSpinner()
        loadCategorySpinner()

        return binding.root
    }

    private fun backToHomeActivity(backButton: ImageView) {
        backButton.setOnClickListener {
            dismiss()
        }
    }

    private fun selectDate(pickDateButton: DatePicker) {
        pickDateButton.setOnClickListener {
            showDatePicker()
        }
    }

    private fun createANewExpense(
        btnCreateExpense: Button,
        edtTitleModal: EditText,
        psvCategory: PowerSpinnerView,
        edtPriceModal: EditText,
        pickDateButton: DatePicker
    ) {
        btnCreateExpense.setOnClickListener {
            val title = edtTitleModal.text.toString()
            val category = psvCategory.text.toString()
            val amount = edtPriceModal.text.toString()
            val color = selectedColorTransaction

            val date =
                "${pickDateButton.month + 1}/${pickDateButton.dayOfMonth}/${pickDateButton.year}"

            val newTransaction = Transaction(id, title, category, amount, date, color, "")

            insertTransaction(transaction = newTransaction)

            dismiss()
        }
    }

    private fun insertTransaction(transaction: Transaction) {
        CoroutineScope(Dispatchers.IO).launch {
            expenseDao.insertExpense(transaction)
            //update list
            (activity as? HomeActivity)?.getTransactions()
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

    private fun showDatePicker() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            requireContext(),
            { _, selectedYear, selectedMonth, selectedDayOfMonth ->
                selectedDate = "${selectedMonth + 1}/$selectedDayOfMonth/$selectedYear"
                Toast.makeText(requireContext(), "Date selected: $selectedDate", Toast.LENGTH_SHORT)
                    .show()
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
