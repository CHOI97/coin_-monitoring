package com.example.coin_monitoring.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.coin_monitoring.db.dao.InterestCoinDAO
import com.example.coin_monitoring.db.entity.InterestCoinEntity

@Database(entities = [InterestCoinEntity::class], version = 1)
abstract class CoinPriceDatabase : RoomDatabase(){
    abstract  fun interestCoinDao() : InterestCoinDAO

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