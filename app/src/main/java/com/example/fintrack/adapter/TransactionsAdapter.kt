package com.example.fintrack.adapter

import android.content.res.ColorStateList
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.fintrack.R
import com.example.fintrack.model.Transaction

class TransactionsAdapter(
    private var transactions: List<Transaction>,
    private val clickListener: (Transaction) -> Unit) :
    RecyclerView.Adapter<TransactionsAdapter.TransactionViewHolder>() {

    class TransactionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageViewCategory: ImageView = itemView.findViewById(R.id.iv_icon)
        val textViewTitle: TextView = itemView.findViewById(R.id.tv_title)
        val textViewCategory: TextView = itemView.findViewById(R.id.tv_category)
        val textViewAmount: TextView = itemView.findViewById(R.id.tv_amount)
        val textViewDate: TextView = itemView.findViewById(R.id.tv_date)
        val viewColor: View = itemView.findViewById(R.id.color_bar)

        fun bind(transaction: Transaction, clickListener: (Transaction) -> Unit) {
            textViewTitle.text = transaction.title
            textViewCategory.text = transaction.category
            textViewAmount.text = transaction.amount
            textViewDate.text = transaction.date
            itemView.setOnClickListener {
                clickListener(transaction)
            }
        }
    }

    private fun getCategoryIcon(category: String): Int {
        return when (category) {
            "Transport" -> R.drawable.ic_car
            "Clothe" -> R.drawable.ic_clothes
            "Credit card" -> R.drawable.ic_credit_card
            "Electricity" -> R.drawable.ic_electricity
            "Gas station" -> R.drawable.ic_gas_station
            "Internet" -> R.drawable.ic_wifi
            "Home" -> R.drawable.ic_home
            "Game control" -> R.drawable.ic_game_control
            "Food" -> R.drawable.ic_food
            else -> R.drawable.ic_loading
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_rv_home, parent, false)
        return TransactionViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: TransactionViewHolder, position: Int) {
        val transaction = transactions[position]
        holder.bind(transaction, clickListener)
        holder.imageViewCategory.setImageResource(getCategoryIcon(transaction.category))
        holder.textViewTitle.text = transaction.title
        holder.textViewCategory.text = transaction.category
        holder.textViewAmount.text = transaction.amount
        holder.textViewDate.text = transaction.date
        holder.viewColor.backgroundTintList = ColorStateList.valueOf(Color.parseColor(transaction.colorTransaction.hexHash))
    }

    override fun getItemCount(): Int {
        return transactions.size
    }

    fun updateTransactions(newTransactions: List<Transaction>) {
        this.transactions = newTransactions
        notifyDataSetChanged()
    }
}
