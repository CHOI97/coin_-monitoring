package com.example.coin_monitoring.view.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.coin_monitoring.db.entity.InterestCoinEntity
import com.example.coin_monitoring.repository.DBRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel: ViewModel(){

    private val dbRepository = DBRepository()

    lateinit var selectedCoinList : LiveData<List<InterestCoinEntity>>

    // CoinListFragment

    fun getAllInterestCoinData() = viewModelScope.launch {
        val coinList = dbRepository.getAllInterestCoinData().asLiveData()
        selectedCoinList = coinList
    }

    fun updateInterestCoinData(interestCoinEntity: InterestCoinEntity) = viewModelScope.launch(Dispatchers.IO) {
        if(interestCoinEntity.selected){
            interestCoinEntity.selected = false
        }else{
            interestCoinEntity.selected = true
        }

        dbRepository.updateInterestCoinData(interestCoinEntity)
    }

    // PriceChangeFragment
    // 1. 관심있다고 선택한 코인 리스트 가져오기
    // 2. 관심있는 코인 리스트를 반복문을 통해 가져오기
    // 3. 저장된 코인 가격 리스트 가져오기
    // 4. 시간마다 어떻게 변경되었는지 알려주는 로직 작성하기

    fun getAllSelectedCoinData() = viewModelScope.launch(Dispatchers.IO) {

        // 1. 관심있다고 선택한 코인 리스트 가져오기

        val selectedCoinList = dbRepository.getAllInterestSelectedCoinData()

        // 2. 관심있는 코인 리스트를 반복문을 통해 가져오기
        for(data in selectedCoinList){

            // 3. 저장된 코인 가격 리스트 가져오기
            val coinName = data.coin_name
            val oneCoinData = dbRepository.getOneSelectedCoinData(coinName).reversed()
            val size = oneCoinData.size


            if(size>1){
                // DB 값이 2개 이상
                // 현재와 15분전 가격을 비교하려면 데이터가 2개 이상은 있어야함.
                val changedPrice = oneCoinData[0].price.toDouble() - oneCoinData[1].price.toDouble()
            }
            if(size>2){
                // DB 값이 3개 이상
                // 30분전 가격은 데이터가 3개 이상
                val changedPrice = oneCoinData[0].price.toDouble() - oneCoinData[2].price.toDouble()
            }
            if(size>3){
                // DB 값이 4개 이상
                // 45분전 가격은 데이터가 4개 이상
                val changedPrice = oneCoinData[0].price.toDouble() - oneCoinData[3].price.toDouble()
            }
        }

    }
}