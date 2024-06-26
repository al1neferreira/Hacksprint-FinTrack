package com.example.fintrack.home

import HomeViewModelFactory
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.fintrack.R
import com.example.fintrack.adapter.CategoryListAdapter
import com.example.fintrack.adapter.TransactionsAdapter
import com.example.fintrack.data.local.CategoryUiData
import com.example.fintrack.data.local.ExpenseUiData
import com.example.fintrack.databinding.ActivityHomeBinding
import com.example.fintrack.db.CategoryDao
import com.example.fintrack.db.CategoryEntity
import com.example.fintrack.db.ExpenseDao
import com.example.fintrack.db.ExpenseDatabase
import com.example.fintrack.fragments.CreateExpenseFragment
import com.example.fintrack.model.ColorTransaction
import com.example.fintrack.model.Transaction
import com.example.fintrack.repo.ExpenseRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private lateinit var adapter: TransactionsAdapter
    private lateinit var homeViewModel: HomeViewModel


    private val db by lazy {
        Room.databaseBuilder(
            applicationContext,
            ExpenseDatabase::class.java, "database-expense"
        ).build()
    }

    private val categoryDao: CategoryDao by lazy {
        db.getCategoryDao()
    }

    private val expenseDao: ExpenseDao by lazy {
        db.getExpenseDao()
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

        homeViewModel = ViewModelProvider(
            this,
            HomeViewModelFactory(ExpenseRepository(db))
        )[HomeViewModel::class.java]


        val rvTransaction = binding.rvTransactions
        val rvCategory = binding.rvCategories

        val categoryAdapter = CategoryListAdapter()
        //val transactionsAdapter = TransactionsAdapter()


        rvCategory.adapter = categoryAdapter
        getCategoriesFromDatabase(categoryAdapter)

        rvTransaction.adapter = adapter

        insertDefaultCategory()
        selectACategory(categoryAdapter)
        setupNewExpense()
        getTransactions()
        updateListTransactions()
        setupToolbar()
        insertDefaultExpenses()

    }

    private fun setupToolbar() {
        binding.toolbarHome.setOnMenuItemClickListener {
            if (it.itemId == R.id.menu_delete_all) {
                deleteAll()
            }
            false
        }
    }

    private fun deleteAll() {
        homeViewModel.deleteAllExpenses()
    }

    private fun selectACategory(categoryAdapter: CategoryListAdapter) {
        categoryAdapter.setOnClickListener { selected ->
            val categoryTemp = categories.map { item ->
                when {
                    item.name == selected.name && !item.isSelected -> item.copy(isSelected = true)
                    item.name == selected.name && item.isSelected -> item.copy(isSelected = false)
                    else -> item
                }
            }
            val transactionTemp =
                if (selected.name != "ALL") {
                    transactions.filter {
                        it.category == selected.name
                    }
                } else {
                    transactions
                }

            categoryAdapter.submitList(categoryTemp)
            //categoryAdapter.submitList(transactionTemp)

        }
    }

    private fun updateListTransactions() {
        homeViewModel.expenseData.observe(this, Observer { expenses ->
            expenses?.let {
                adapter.updateTransactions(it)
            }
        })
    }

    private fun insertDefaultExpenses() {
        val transaction = transactions.map {
            Transaction(
                title = it.title,
                category = it.category,
                amount = it.amount,
                date = it.date,
                colorTransaction = it.colorTransaction,
                image = it.image
            )
        }
        GlobalScope.launch(Dispatchers.IO) {
            expenseDao.insertAll(transaction)

        }
    }


    @OptIn(DelicateCoroutinesApi::class)
    private fun insertDefaultCategory() {
        val categoriesEntity = categories.map {
            CategoryEntity(
                name = it.name,
                isSelected = it.isSelected
            )
        }

        GlobalScope.launch(Dispatchers.IO) {
            categoryDao.insertAll(categoriesEntity)
        }

    }

    @OptIn(DelicateCoroutinesApi::class)
    private fun getCategoriesFromDatabase(adapter: CategoryListAdapter) {
        GlobalScope.launch(Dispatchers.IO) {
            val categoriesFromDb: List<CategoryEntity> = categoryDao.getAll()
            val categoriesUiData = categoriesFromDb.map {
                CategoryUiData(
                    name = it.name,
                    isSelected = it.isSelected
                )
            }
            adapter.submitList(categoriesUiData)
        }
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
            R.id.menu_delete_all -> true
            R.id.menu_light -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}

val categories = listOf(
    CategoryUiData(
        name = "ALL", isSelected = false
    ),
    CategoryUiData(name = "FOOD", isSelected = false),
    CategoryUiData(name = "TRANSPORT", isSelected = false),
    CategoryUiData(name = "ENTERTAINMENT", isSelected = false),
    CategoryUiData(name = "HEALTH", isSelected = false),
    CategoryUiData(name = "INTERNET", isSelected = false),
    CategoryUiData(name = "HOME", isSelected = false),
    CategoryUiData(name = "CLOTHE", isSelected = false),
    CategoryUiData(name = "ELETRICITY", isSelected = false),
    CategoryUiData(name = "GAS STATION", isSelected = false),
    CategoryUiData(name = "GAMES", isSelected = false),
    CategoryUiData(name = "OTHERS", isSelected = false),
)

val transactions = listOf(
    Transaction(
        0,
        "Uber",
        "TRANSPORT",
        "50.00",
        "20-05-2024",
        ColorTransaction("Silver", "C0C0C0", "blackHex"),
        "image0"
    ),
    Transaction(
        1,
        "Dinner",
        "FOOD",
        "100.00",
        "23-05-2024",
        ColorTransaction("Red", "FF0000", "whiteHex"),
        "image1"
    ),
    Transaction(
        2,
        "Cinema",
        "ENTERTAINMENT",
        "35.00",
        "13-05-2024",
        ColorTransaction("Lime", "00FF00", "blackHex"), "image2"
    ),
    Transaction(
        3,
        "Gym",
        "HEALTH",
        "85.00",
        "10-05-2024",
        ColorTransaction("Yellow", "FFFF00", "blackHex"), "image3"

    ),
    Transaction(
        4,
        "Amazon",
        "OTHER",
        "20.00",
        "03-05-2024",
        ColorTransaction("Blue", "0000FF", "whiteHex"), "image3"
    )
)

