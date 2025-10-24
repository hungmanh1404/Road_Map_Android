package com.example.road_map_android.data.vo

sealed class TransactionListItem {
    data class Title(val month: String) : TransactionListItem()

    data class Transaction(
        val id: String,
        val date: String,
        val amount: String,
        val type: String,
        val isPositive: Boolean,
        var isExpanded: Boolean = false,
        val details: List<Detail> = emptyList()
    ) : TransactionListItem()

    data class Detail(
        val label: String,
        val value: String
    ) : TransactionListItem()
}
