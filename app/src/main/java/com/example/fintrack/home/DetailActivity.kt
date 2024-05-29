package com.example.fintrack.home

import HomeViewModelFactory
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.example.fintrack.R
import com.example.fintrack.databinding.ActivityDetailBinding
import com.example.fintrack.db.ExpenseDatabase
import com.example.fintrack.fragments.EditExpenseFragment
import com.example.fintrack.model.Transaction
import com.example.fintrack.repo.ExpenseRepository
import com.example.fintrack.viewModel.ExpenseViewModel
import java.lang.IllegalArgumentException

class DetailActivity : AppCompatActivity() {

    private lateinit var expenseViewModel: ExpenseViewModel
    private lateinit var binding: ActivityDetailBinding
    private lateinit var homeViewModel: HomeViewModel
    private lateinit var transaction: Transaction

    private val db by lazy {
        Room.databaseBuilder(
            applicationContext,
            ExpenseDatabase::class.java, "database-expense"
        ).build()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val toolbar: Toolbar = findViewById(R.id.topAppBar)
        setSupportActionBar(toolbar)

        homeViewModel = ViewModelProvider(
            this,
            HomeViewModelFactory(ExpenseRepository(db))
        )[HomeViewModel::class.java]

        //supportActionBar?.setHomeButtonEnabled(true)
        //supportActionBar?.setDisplayHomeAsUpEnabled(true)

        transaction = intent.getSerializableExtra("TRANSACTION") as? Transaction
            ?: throw IllegalArgumentException("Transaction is required")

        populateDetails(transaction)

        setupEditExpense()
        setupToolbar()

        val btnBack = binding.btnBack

        btnBack.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }

    }

    private fun setupToolbar() {
        binding.topAppBar.setOnMenuItemClickListener {
            if (it.itemId == R.id.menu_delete) {
                deleteSingleExpense(transaction)
                val intent = Intent(this, HomeActivity::class.java)
                startActivity(intent)
                true
            } else {
                false
            }
        }
    }

    private fun deleteSingleExpense(transaction: Transaction) {
        homeViewModel.deleteExpense(transaction)
    }


    private fun populateDetails(transaction: Transaction) {
        binding.tvDescDetail.text = transaction.title
        binding.tvDescCategoryDetail.text = transaction.category
        binding.tvDatePickerDetail.text = transaction.date
        binding.tvAmountValueDetail.text = transaction.amount
    }

    private fun setupEditExpense() {
        binding.btnEditDetail.setOnClickListener {
            openModalEditExpense(transaction)
        }
    }


    private fun openModalEditExpense(transaction: Transaction) {
        val dialog = EditExpenseFragment()
        val bundle = Bundle().apply {
           putSerializable("transaction", transaction)
            putInt("transactionId", transaction.id)
        }
        dialog.arguments = bundle
        dialog.show(supportFragmentManager, "EditExpenseFragment")
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_detail, menu)
        return true
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_delete -> {
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }


}





