package com.example.fintrack.home

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.example.fintrack.R
import com.example.fintrack.databinding.ActivityDetailBinding
import com.example.fintrack.fragments.EditExpenseFragment


class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)


        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        setupEditExpense()

    }

    private fun setupEditExpense() {
        binding.btnEditDetail.setOnClickListener {
            openModalEditExpense()
        }
    }

    private fun openModalEditExpense() {
        val dialog = EditExpenseFragment()
        dialog.show(supportFragmentManager, "CreateExpenseFragment")
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