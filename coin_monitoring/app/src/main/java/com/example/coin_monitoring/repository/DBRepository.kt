package com.example.coin_monitoring.repository

import com.example.coin_monitoring.App
import com.example.coin_monitoring.db.CoinPriceDatabase
import com.example.coin_monitoring.db.entity.InterestCoinEntity
import com.example.coin_monitoring.db.entity.SelectedCoinPriceEntity

class DBRepository {
    val context = App.context()
    val db = CoinPriceDatabase.getDatabase(context)

    // InterestCoin

    // 전체 코인 데이터 가져오기
    fun getAllInterestCoinData() = db.interestCoinDao().getAllData()

    // 코인 데이터 넣기
    fun insertInterestCoinData(interestCoinEntity: InterestCoinEntity)=db.interestCoinDao().insert(interestCoinEntity)

    // 코인 데이터 업데이트
    fun updateInterestCoinData(interestCoinEntity: InterestCoinEntity)=db.interestCoinDao().update(interestCoinEntity)

    // 사용자가 관심있어한 코인만 가져오기
    fun getAllInterestSelectedCoinData() = db.interestCoinDao().getSelectedData()

    // Coin Price

    fun getAllCoinPriceData() = db.selectedCoinDao().getAllData()

    fun insertCoinPriceData(selectedCoinPriceEntity: SelectedCoinPriceEntity) = db.selectedCoinDao().insert(selectedCoinPriceEntity)

    fun getOneSelectedCoinData(coinName: String) = db.selectedCoinDao().getOneCoinData(coinName)
}