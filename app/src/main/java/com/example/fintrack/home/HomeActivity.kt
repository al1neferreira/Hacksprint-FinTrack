package com.example.fintrack.home

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.RecyclerView
import com.example.fintrack.R
import com.example.fintrack.adapter.TransactionsAdapter
import com.example.fintrack.databinding.ActivityHomeBinding
import com.example.fintrack.model.Transactions


class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityHomeBinding.inflate(layoutInflater)
        val view = binding.root
        super.onCreate(savedInstanceState)
        setContentView(view)

        val toolbar: Toolbar = findViewById(R.id.toolbar_home)
        setSupportActionBar(toolbar)

        recyclerView = findViewById(R.id.rv_transactions)

        val transactions = listOf(
            Transactions("Market", "Food", "R$50,00", "17-05-2024", "ic_food"),
            Transactions("Gasoline", "Transportation", "R$30,00", "16-05-2024", "ic_car")
        )

        val adapter = TransactionsAdapter(transactions)
        recyclerView.adapter = adapter
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_home, menu)
        return true
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_more -> {
                true
            }

            R.id.menu_light -> {
                true
            }

            else -> super.onOptionsItemSelected(item)
        }

    }
}