package com.example.fintrack.home

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.room.Room
import com.example.fintrack.R
import com.example.fintrack.adapter.TransactionsAdapter
import com.example.fintrack.databinding.ActivityHomeBinding
import com.example.fintrack.db.ExpenseDao
import com.example.fintrack.db.ExpenseDatabase
import com.example.fintrack.fragments.CreateExpenseFragment
import com.example.fintrack.model.Transaction
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private lateinit var adapter: TransactionsAdapter
    private lateinit var expenseDao: ExpenseDao
    private val homeViewModel: HomeViewModel by viewModels()

    private val db by lazy {
        Room.databaseBuilder(
            applicationContext,
            ExpenseDatabase::class.java, "database-expense"
        ).build()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val toolbar: Toolbar = findViewById(R.id.toolbar_home)
        setSupportActionBar(toolbar)

        adapter = TransactionsAdapter(emptyList()) { transaction ->
            openDetailActivity(transaction)
        }
        binding.rvTransactions.adapter = adapter

        expenseDao = db.getExpenseDao()


        setupNewExpense()
        getTransactions()

        homeViewModel.expenseData.observe(this, Observer { expenses ->
            expenses?.let {
                adapter.updateTransactions(it)
            }
        })
    }

     fun getTransactions() {
        CoroutineScope(Dispatchers.IO).launch {
            val listTransactions = expenseDao.getAllExpense().collect { transactions ->
                homeViewModel.updateTransactions(transactions)
            }
        }
    }

    private fun setupNewExpense() {
        binding.fabModalNewExpense.setOnClickListener {
            openModalNewExpense()
        }
    }

    private fun openModalNewExpense() {
        val dialog = CreateExpenseFragment()
        dialog.show(supportFragmentManager, "CreateExpenseFragment")
    }

    private fun openDetailActivity(transaction: Transaction) {
        val intent = Intent(this, DetailActivity::class.java).apply {
            putExtra("TRANSACTION", transaction)
        }
        startActivity(intent)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_home, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_more -> true
            R.id.menu_light -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}
