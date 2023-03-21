package com.example.coin_monitoring.view.intro

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coin_monitoring.dataStore.MyDataStore
import kotlinx.coroutines.launch
import timber.log.Timber

class IntroViewModel: ViewModel() {

    private val _first = MutableLiveData<Boolean>()
    val first : LiveData<Boolean>
        get() = _first
    fun checkFirstFlag() = viewModelScope.launch{
        val getData = MyDataStore().getFirstData()
        _first.value = getData
        Timber.d(getData.toString())
    }
}