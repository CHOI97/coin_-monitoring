package com.example.coin_monitoring.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.work.*
import com.example.coin_monitoring.background.GetCoinPriceRecentContractedWorkManager
import com.example.coin_monitoring.view.main.MainActivity
import com.example.coin_monitoring.databinding.ActivitySelectBinding
import com.example.coin_monitoring.view.adapter.SelectRVAdapter
import timber.log.Timber
import java.util.concurrent.TimeUnit

class SelectActivity : AppCompatActivity() {

    private val viewModel: SelectViewModel by viewModels()
    private lateinit var binding : ActivitySelectBinding
    private lateinit var selectRVAdapter: SelectRVAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySelectBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel.getCurrentCoinList()
        viewModel.currentPriceResult.observe(this , Observer{
            selectRVAdapter = SelectRVAdapter(this, it)
            binding.coinListRV.adapter = selectRVAdapter
            binding.coinListRV.layoutManager = LinearLayoutManager(this)
            Timber.d(it.toString())
        })

        binding.laterTextArea.setOnClickListener{
            viewModel.setUpFirstFlag()
            viewModel.saveSelectedCoinList(selectRVAdapter.selectedCoinList)
            // 만약 코인이 엄청 많을 경우에는? 일부만 저장되고 나머지가 저장이 안되었을 경우
            // 다 저장되고 MainActivity 로 넘겨주는 방법?
            // LiveData 를 사용하여 Observer 로 관찰
        }
        viewModel.save.observe(this, Observer{
            if(it.equals("done")){

                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)

                // 가장 처음으로 우리가 저장한 코인 정보가 시작되는 시점
                saveInterestCoinDataPeriodic()
            }
        })
    }

    private fun saveInterestCoinDataPeriodic(){
        val myWork = PeriodicWorkRequest.Builder(
            GetCoinPriceRecentContractedWorkManager::class.java,
            15,
            TimeUnit.MINUTES
        ).build()
        WorkManager.getInstance(this).enqueueUniquePeriodicWork(
            "GetCoinPriceRecentContractedWorkManager",
        ExistingPeriodicWorkPolicy.KEEP
            ,myWork)
    }
}