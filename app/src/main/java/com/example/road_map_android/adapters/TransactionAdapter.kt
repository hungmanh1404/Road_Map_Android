package com.example.road_map_android.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.road_map_android.data.vo.TransactionListItem
import com.example.road_map_android.databinding.ItemTransactionContentBinding
import com.example.road_map_android.databinding.ItemTransactionDetailBinding
import com.example.road_map_android.databinding.ItemTransactionTitleBinding

class TransactionAdapter(
    private var items: MutableList<TransactionListItem>,
    private val onTransactionClick: (Int) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val TYPE_TITLE = 0
        private const val TYPE_TRANSACTION = 1
        private const val TYPE_DETAIL = 2
    }

    override fun getItemViewType(position: Int): Int {
        return when (items[position]) {
            is TransactionListItem.Title -> TYPE_TITLE
            is TransactionListItem.Transaction -> TYPE_TRANSACTION
            is TransactionListItem.Detail -> TYPE_DETAIL
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

            TYPE_TRANSACTION -> {
                val binding = ItemTransactionContentBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                TransactionViewHolder(binding)
            }

            TYPE_DETAIL -> {
                val binding = ItemTransactionDetailBinding.inflate(
                    LayoutInflater.from(parent.context)
                )
                DetailViewHolder(binding)
            }

            else -> throw IllegalArgumentException("Invalid viewType: $viewType")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (val item = items[position]) {
            is TransactionListItem.Title -> {
                (holder as TitleViewHolder).bind(item)
            }

            is TransactionListItem.Transaction -> {
                (holder as TransactionViewHolder).bind(item, position)
            }

            is TransactionListItem.Detail -> {
                (holder as DetailViewHolder).bind(item)
            }
        }
    }

    override fun getItemCount(): Int = items.size

    inner class TitleViewHolder(private val binding: ItemTransactionTitleBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: TransactionListItem.Title) {
            binding.run {
                tvTitle.text = item.month
            }
        }
    }

    inner class TransactionViewHolder(private val binding: ItemTransactionContentBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: TransactionListItem.Transaction, position: Int) {
            binding.run {
                tvDate.text = item.date
                tvAmount.text = item.amount
                tvType.text = item.type
                val amountColor = if (item.isPositive) {
                    itemView.context.getColor(android.R.color.holo_green_dark)
                } else {
                    itemView.context.getColor(android.R.color.holo_red_dark)
                }
                tvAmount.setTextColor(amountColor)
                tvExpandIcon.text = if (item.isExpanded) "▼" else "▶"
            }
            itemView.setOnClickListener {
                onTransactionClick(position)
            }
        }
    }

    inner class DetailViewHolder(private val binding: ItemTransactionDetailBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: TransactionListItem.Detail) {
            binding.run {
                tvDetailLabel.text = item.label
                tvDetailValue.text = item.value
            }
        }
    }

    fun toggleTransaction(position: Int) {
        val item = items[position]
        if (item !is TransactionListItem.Transaction) return

        if (item.isExpanded) {
            // Đang mở → ẩn detail
            item.isExpanded = false

            // Xóa tất cả detail ngay sau transaction
            val removeIndex = position + 1
            while (removeIndex < items.size && items[removeIndex] is TransactionListItem.Detail) {
                items.removeAt(removeIndex)
            }
            notifyItemChanged(position)
            notifyItemRangeRemoved(position + 1, item.details.size)
        } else {
            // Đang đóng → mở detail
            item.isExpanded = true

            // Chèn detail vào ngay sau transaction
            val details = item.details.map { TransactionListItem.Detail(it.label, it.value) }
            items.addAll(position + 1, details)

            notifyItemChanged(position)
            notifyItemRangeInserted(position + 1, details.size)
        }
    }
}
