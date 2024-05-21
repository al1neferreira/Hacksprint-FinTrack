package com.example.fintrack.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.fintrack.R
import com.example.fintrack.model.Transactions

class TransactionsAdapter(private val transactions: List<Transactions>) :
    RecyclerView.Adapter<TransactionsAdapter.TransactionViewHolder>() {

    class TransactionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageViewCategory: ImageView = itemView.findViewById(R.id.iv_icon)
        val textViewTitle: TextView = itemView.findViewById(R.id.tv_title)
        val textViewCategory: TextView = itemView.findViewById(R.id.tv_category)
        val textViewAmount: TextView = itemView.findViewById(R.id.tv_amount)
        val textViewDate: TextView = itemView.findViewById(R.id.tv_date)
    }

    private fun getCategoryIcon(category: String): Int {
        return when (category) {
            "Transportation" -> R.drawable.ic_car
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

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TransactionViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_rv_home, parent, false)
        return TransactionViewHolder(itemView)
    }

    override fun onBindViewHolder(
        holder: TransactionViewHolder,
        position: Int
    ) {
        val transaction = transactions[position]
        holder.imageViewCategory.setImageResource(getCategoryIcon(transaction.category))
        holder.textViewTitle.text = transaction.title
        holder.textViewCategory.text = transaction.category
        holder.textViewAmount.text = transaction.amount
        holder.textViewDate.text = transaction.date
    }

    override fun getItemCount(): Int {
        return transactions.size
    }
}

