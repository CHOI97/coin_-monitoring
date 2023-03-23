package com.example.coin_monitoring.db

import android.content.Context
import androidx.room.*
import com.example.coin_monitoring.db.dao.InterestCoinDAO
import com.example.coin_monitoring.db.dao.SelectedCoinPriceDAO
import com.example.coin_monitoring.db.entity.DateConverters
import com.example.coin_monitoring.db.entity.InterestCoinEntity
import com.example.coin_monitoring.db.entity.SelectedCoinPriceEntity

@Database(entities = [InterestCoinEntity::class, SelectedCoinPriceEntity::class], version = 2)
@TypeConverters(DateConverters::class)
abstract class CoinPriceDatabase : RoomDatabase(){
    abstract  fun interestCoinDao() : InterestCoinDAO
    abstract  fun selectedCoinDao() : SelectedCoinPriceDAO

    companion object{

        @Volatile
        private var INSTANCE : CoinPriceDatabase? =null
        fun getDatabase(
            context: Context
        ): CoinPriceDatabase{
            return INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    CoinPriceDatabase::class.java,
                    "coin_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}