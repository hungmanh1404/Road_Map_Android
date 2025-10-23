package com.example.road_map_android.data.vo

sealed class TransactionItem {
    data class Title(val month: String) : TransactionItem()
    data class Transaction(
        val id: String,
        val date: String,
        val amount: String,
        val isPositive: Boolean,
        val type: String
    ) : TransactionItem()
}
