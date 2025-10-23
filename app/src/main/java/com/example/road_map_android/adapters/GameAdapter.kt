package com.example.road_map_android.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.road_map_android.data.vo.Game
import com.example.road_map_android.databinding.ItemGameBinding

class GameAdapter(
    private var items: List<Game>,
) : RecyclerView.Adapter<GameAdapter.ItemViewHolder>() {

    inner class ItemViewHolder(private val binding: ItemGameBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            val displayMetrics = binding.root.context.resources.displayMetrics
            val screenWidth = displayMetrics.widthPixels

            // Giả sử bạn muốn hiển thị 2 item trên 1 hàng (chia đôi)
            val itemWidth = (screenWidth / 3.3).toInt() - 40 // có thể chỉnh tỉ lệ tùy bạn

            val layoutParams = binding.ctContainer.layoutParams
            layoutParams.width = itemWidth
            binding.ctContainer.layoutParams = layoutParams
        }

        fun bind(item: Game) {
            binding.run {
                tvNameGame.text = item.name
                tvPrice.text = item.priceLabel
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ItemViewHolder {
        val binding =
            ItemGameBinding.inflate(LayoutInflater.from(parent.context), parent, false)
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
