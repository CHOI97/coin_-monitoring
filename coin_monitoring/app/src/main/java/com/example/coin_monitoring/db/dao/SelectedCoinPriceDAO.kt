package com.example.coin_monitoring.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.coin_monitoring.db.entity.SelectedCoinPriceEntity

@Dao
interface SelectedCoinPriceDAO{

    // getAllData
    @Query("SELECT * FROM selected_coin_price_table")
    fun getAllData() : List<SelectedCoinPriceEntity>

    // Insert
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(selectedCoinPriceEntity: SelectedCoinPriceEntity)

    // 하나의 코인에 대해서 저장된 정보를 가져오는 친구..?
    // BTC 15 , 30 , 45 ... -> BTC 관련한 데이터 가져오기 -> List<BTC> -> 현재가격이랑 15 , 30 , 45 가격 변동 추이 DB에 저장된 값이랑 비교
    @Query("SELECT * FROM selected_coin_price_table WHERE coinName = :coinName")
    fun getOneCoinData(coinName: String) : List<SelectedCoinPriceEntity>
}