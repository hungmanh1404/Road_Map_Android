package com.example.road_map_android.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.road_map_android.data.vo.Job
import com.example.road_map_android.databinding.ItemJobSelectBinding

class JobSelectAdapter(
    private var items: List<Job>,
    private val onItemClick: (Job) -> Unit
) : RecyclerView.Adapter<JobSelectAdapter.ItemViewHolder>() {

    inner class ItemViewHolder(private val binding: ItemJobSelectBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.viewSelect.setOnClickListener {
                onItemClick(items[adapterPosition])
            }
        }

        fun bind(item: Job) {
            binding.tvTitle.text = item.name
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ItemViewHolder {
        val binding =
            ItemJobSelectBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: ItemViewHolder,
        position: Int
    ) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

}
