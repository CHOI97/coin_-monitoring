package com.example.coin_monitoring.db.dao

import androidx.room.*
import com.example.coin_monitoring.db.entity.InterestCoinEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface InterestCoinDAO {

    // getAllData
    // Flow ( Coroutine ) ROOM 과 같이 사용하면 데이터의 변경 사항을 감지하기 용이하다.
    @Query("SELECT * FROM interest_coin_table")
    fun getAllData() : Flow<List<InterestCoinEntity>>

    // Insert
    @Insert(onConflict =  OnConflictStrategy.IGNORE)
    fun insert(interestCoinEntity: InterestCoinEntity)

    // Update
    @Update
    fun update(interestCoinEntity: InterestCoinEntity)
    // getSelectedCoinList -> 내가 관심있어하는 코인 데이터 가져오기
    // coin1 , coin2 , coin3 -> coin1 data , coin2 data , coin data
    @Query("SELECT * FROM interest_coin_table WHERE selected = :selected")
    fun getSelectedData(selected: Boolean = true): List<InterestCoinEntity>
}