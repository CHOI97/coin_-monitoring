package com.example.coin_monitoring.dataModel

import android.view.SurfaceControl.Transaction

data class RecentPriceData(
    val transaction_date: String,
    val type: String,
    val units_traded: String,
    val price: String,
    val total: String
)
