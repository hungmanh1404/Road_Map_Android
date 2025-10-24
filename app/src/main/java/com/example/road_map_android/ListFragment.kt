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
import com.example.road_map_android.data.vo.TransactionListItem
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

    val transactionData = mutableListOf<TransactionListItem>(
        TransactionListItem.Title("December 2019"),
        TransactionListItem.Transaction(
            id = "TX-1001",
            date = "29",
            amount = "Rs2.00",
            type = "Business",
            isPositive = true,
            details = listOf(
                TransactionListItem.Detail("ID", "TX-1001"),
                TransactionListItem.Detail("Location", "New Delhi"),
                TransactionListItem.Detail("Time", "09:45 AM")
            )
        ),
        TransactionListItem.Title("October 2019"),
        TransactionListItem.Transaction(
            id = "TX-1002",
            date = "25",
            amount = "Rs11.50",
            type = "Shopping",
            isPositive = false,
            details = listOf(
                TransactionListItem.Detail("ID", "TX-1002"),
                TransactionListItem.Detail("Location", "Mumbai"),
                TransactionListItem.Detail("Time", "07:20 PM")
            )
        ),
        TransactionListItem.Title("September 2019"),
        TransactionListItem.Transaction(
            id = "TX-1003",
            date = "15",
            amount = "Rs5.25",
            type = "Food",
            isPositive = false,
            details = listOf(
                TransactionListItem.Detail("ID", "TX-1003"),
                TransactionListItem.Detail("Location", "Bangalore"),
                TransactionListItem.Detail("Time", "12:30 PM"),
                TransactionListItem.Detail("ID", "TX-1003"),
                TransactionListItem.Detail("Location", "Bangalore"),
                TransactionListItem.Detail("Time", "12:30 PM")
            )
        )
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

        transactionAdapter = TransactionAdapter(
            items = transactionData,
            onTransactionClick = { position ->
                transactionAdapter?.toggleTransaction(position)
            }
        )

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
