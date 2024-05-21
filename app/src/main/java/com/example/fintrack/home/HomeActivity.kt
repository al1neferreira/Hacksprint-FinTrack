package com.example.fintrack.home

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import com.example.fintrack.R
import com.example.fintrack.adapter.TransactionsAdapter
import com.example.fintrack.databinding.ActivityHomeBinding
import com.example.fintrack.fragments.CreateExpenseFragment

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private val homeViewModel: HomeViewModel by viewModels()
    private lateinit var adapter: TransactionsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val toolbar: Toolbar = findViewById(R.id.toolbar_home)
        setSupportActionBar(toolbar)

        adapter = TransactionsAdapter(emptyList())
        binding.rvTransactions.adapter = adapter

        setupNewExpense()

        homeViewModel.expenseData.observe(this, Observer { expenses ->
            expenses?.let {
                adapter.updateTransactions(it)
            }
        })
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
