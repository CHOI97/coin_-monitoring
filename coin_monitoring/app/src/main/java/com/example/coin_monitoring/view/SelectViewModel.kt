package com.example.coin_monitoring.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coin_monitoring.dataModel.CurrentPrice
import com.example.coin_monitoring.dataModel.CurrentPriceResult
import com.example.coin_monitoring.dataStore.MyDataStore
import com.example.coin_monitoring.db.entity.InterestCoinEntity
import com.example.coin_monitoring.network.model.CurrentPriceList
import com.example.coin_monitoring.repository.DBRepository
import com.example.coin_monitoring.repository.NetworkRepository
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber

class SelectViewModel : ViewModel() {

    private val newWorkRepository = NetworkRepository()
    private val dbRepository = DBRepository()
    private lateinit var currentPriceResultList: ArrayList<CurrentPriceResult>

    // LiveData
    private val _currentPriceResult = MutableLiveData<List<CurrentPriceResult>>()
    val currentPriceResult: LiveData<List<CurrentPriceResult>> get() = _currentPriceResult
    private val _saved = MutableLiveData<String>()
    val save: LiveData<String>
     get() = _saved


    fun getCurrentCoinList() = viewModelScope.launch {
        val result = newWorkRepository.getCurrentCoinList()
        currentPriceResultList = ArrayList()
        for (coin in result.data) {
            try {
                val gson = Gson()
                val gsonToJson = gson.toJson(result.data.get(coin.key))
                val gsonFromJson = gson.fromJson(gsonToJson, CurrentPrice::class.java)
                val currentPriceResult = CurrentPriceResult(coin.key, gsonFromJson)
                Timber.d(currentPriceResult.toString())
                currentPriceResultList.add(currentPriceResult)
            } catch (e: java.lang.Exception) {
                Timber.d(e.toString())
            }
        }
        _currentPriceResult.value = currentPriceResultList
    }

    fun setUpFirstFlag() = viewModelScope.launch {
        MyDataStore().setupFirstData()
    }

    // DB에 데이터 저장
    // Dispatchers.IO  -> Room 구성요소나 NetWork I/O
    fun saveSelectedCoinList(selectedCoinList : ArrayList<String>) = viewModelScope.launch (Dispatchers.IO){

        // 1. 전체 코인 데이터를 가져와서
        for(coin in currentPriceResultList){
            Timber.d(coin.toString())
            // 2. 내가 선택한 코인인지 아닌지 구분해서
            // 포함시 TRUE / 포함하지 않으면 FALSE
            val selected = selectedCoinList.contains(coin.coinName)

            val interestedCoinEntity = InterestCoinEntity(
                0,
                coin.coinName,
                coin.coinInfo.opening_price,
                coin.coinInfo.closing_price,
                coin.coinInfo.min_price,
                coin.coinInfo.max_price,
                coin.coinInfo.units_traded,
                coin.coinInfo.acc_trade_value,
                coin.coinInfo.prev_closing_price,
                coin.coinInfo.units_traded_24H,
                coin.coinInfo.acc_trade_value_24H,
                coin.coinInfo.fluctate_24H,
                coin.coinInfo.fluctate_rate_24H,
                selected
            )

            // 3. 저장
            interestedCoinEntity.let{
                dbRepository.insertInterestCoinData(it)
            }
        }
        withContext(Dispatchers.Main){
            _saved.value = "done"
        }
    }

}