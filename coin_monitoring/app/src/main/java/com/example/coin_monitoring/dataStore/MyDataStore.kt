package com.example.coin_monitoring.dataStore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import com.example.coin_monitoring.App

class MyDataStore {
    private val context = App.context()

    companion object{
        private val Context.dataStore : DataStore<Preferences> by preferencesDataStore("user_pref")
    }

    private val mDataStore : DataStore<Preferences> = context.dataStore

    private val FIRST_FLAG = booleanPreferencesKey("FIRST_FLAG")

    // MainActivity 로 이동할 때 -> True 로 변경

    // 처음 접속하는 유저가 아니면 TRUE , 처음 접속하는 유저 일 경우 -> False
    suspend fun setupFirstData(){
        mDataStore.edit{ preferences ->
            preferences[FIRST_FLAG] = true
        }
    }

    suspend fun getFirstData() : Boolean{
        var currentValue = false
        mDataStore.edit{ preferences ->
            currentValue = preferences[FIRST_FLAG] ?: false
        }
        return currentValue
    }
}