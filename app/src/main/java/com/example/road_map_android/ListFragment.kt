package com.example.road_map_android

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.road_map_android.adapters.GameAdapter
import com.example.road_map_android.adapters.TransactionAdapter
import com.example.road_map_android.data.vo.Game
import com.example.road_map_android.data.vo.TransactionItem
import com.example.road_map_android.databinding.FragmentListBinding

class ListFragment : Fragment() {

    val gameList = listOf(
        Game(
            name = "Asphalt 8",
            imageUrl = "https://play-lh.googleusercontent.com/abc123-asphalt8-icon=w240-h480-rw",
            priceLabel = "MIỄN PHÍ"
        ),
        Game(
            name = "Subway Surfers",
            imageUrl = "https://play-lh.googleusercontent.com/xyz456-subway-icon=w240-h480-rw",
            priceLabel = "MIỄN PHÍ"
        ),
        Game(
            name = "Fruit Ninja",
            imageUrl = "https://play-lh.googleusercontent.com/pqr789-fruitninja-icon=w240-h480-rw",
            priceLabel = "MIỄN PHÍ"
        ),
        Game(
            name = "Minecraft",
            imageUrl = "https://play-lh.googleusercontent.com/minecraft-icon=w240-h480-rw",
            priceLabel = "179.000₫"
        )
    )

    val fakeData = listOf(
        TransactionItem.Title("December 2019"),
        TransactionItem.Transaction("29", "Rs2.00", "demo", true, "1"),
        TransactionItem.Title("October 2019"),
        TransactionItem.Transaction("29", "Rs2.00", "demo", true, "2"),
        TransactionItem.Transaction("25", "Rs12.00", "demo", false, "3"),
        TransactionItem.Transaction("25", "Rs11.50", "demo", false, "4")
    )

    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding

    private var gameAdapter: GameAdapter? = null
    private var transactionAdapter: TransactionAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentListBinding.inflate(inflater, container, false)
        return binding?.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    private fun initViews() {
        gameAdapter = GameAdapter(
            items = gameList
        )

        transactionAdapter = TransactionAdapter(fakeData)

        binding?.run {
            recyclerViewGame.layoutManager =
                LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
            recyclerViewGame.adapter = gameAdapter

            recyclerViewTransaction.layoutManager = LinearLayoutManager(requireContext())
            recyclerViewTransaction.adapter = transactionAdapter
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
