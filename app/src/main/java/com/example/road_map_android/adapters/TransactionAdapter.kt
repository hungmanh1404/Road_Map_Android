package com.example.road_map_android.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.road_map_android.data.vo.TransactionItem
import com.example.road_map_android.databinding.ItemTransactionContentBinding
import com.example.road_map_android.databinding.ItemTransactionTitleBinding

class TransactionAdapter(
    private val items: List<TransactionItem>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val TYPE_TITLE = 0
        private const val TYPE_TRANSACTION = 1
    }

    override fun getItemViewType(position: Int): Int {
        return when (items[position]) {
            is TransactionItem.Title -> TYPE_TITLE
            is TransactionItem.Transaction -> TYPE_TRANSACTION
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TYPE_TITLE -> {
                val binding = ItemTransactionTitleBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                TitleViewHolder(binding)
            }

            else -> {
                val binding = ItemTransactionContentBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                TransactionViewHolder(binding)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (val item = items[position]) {
            is TransactionItem.Title -> (holder as TitleViewHolder).bind(item)
            is TransactionItem.Transaction -> (holder as TransactionViewHolder).bind(item)
        }
    }

    override fun getItemCount() = items.size

    inner class TitleViewHolder(private val binding: ItemTransactionTitleBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: TransactionItem.Title) {
            binding.tvTitle.text = item.month
        }
    }

    inner class TransactionViewHolder(private val binding: ItemTransactionContentBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: TransactionItem.Transaction) {
            binding.tvDate.text = item.date
            binding.tvAmount.text = item.amount
            binding.tvAmount.setTextColor(
                if (item.isPositive) Color.parseColor("#4CAF50") else Color.RED
            )
            binding.tvType.text = item.type
        }
    }
}
