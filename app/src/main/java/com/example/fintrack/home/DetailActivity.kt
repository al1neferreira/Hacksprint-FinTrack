package com.example.fintrack.home

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.example.fintrack.R
import com.example.fintrack.databinding.ActivityDetailBinding
import com.example.fintrack.fragments.EditExpenseFragment
import com.example.fintrack.model.Transaction
import com.example.fintrack.viewModel.ExpenseViewModel

class DetailActivity: AppCompatActivity() {

    private lateinit var expenseViewModel: ExpenseViewModel
    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val transaction = intent.getSerializableExtra("TRANSACTION") as? Transaction
        transaction?.let { populateDetails(it) }


        setupEditExpense()

        val btnBack = binding.btnBack

        btnBack.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }

    }



    private fun populateDetails(transaction: Transaction) {
        binding.tvDescDetail.text = transaction.title
        binding.tvDescCategoryDetail.text = transaction.category
        binding.tvDatePickerDetail.text = transaction.date
        binding.tvAmountValueDetail.text = transaction.amount
    }

    private fun setupEditExpense() {
        binding.btnEditDetail.setOnClickListener {
            openModalEditExpense()
        }
    }


    private fun openModalEditExpense() {
        val dialog = EditExpenseFragment()
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

            R.id.menu_share -> {
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }


}





