package com.example.coin_monitoring.network.model

import com.example.coin_monitoring.dataModel.RecentPriceData

data class RecentCoinPriceList(
    val status: String,
    val data : List<RecentPriceData>
)