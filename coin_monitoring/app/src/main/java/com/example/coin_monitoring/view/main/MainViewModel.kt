package com.example.coin_monitoring.view.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.coin_monitoring.db.entity.InterestCoinEntity
import com.example.coin_monitoring.repository.DBRepository
import kotlinx.coroutines.launch

class MainViewModel: ViewModel(){

    private val dbRepository = DBRepository()

    lateinit var selectedCoinList : LiveData<List<InterestCoinEntity>>

    fun getAllInterestCoinData() = viewModelScope.launch {
        val coinList = dbRepository.getAllInterestCoinData().asLiveData()
        selectedCoinList = coinList
    }
}