package com.example.coin_monitoring.view.setting

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.coin_monitoring.R
import com.example.coin_monitoring.databinding.ActivitySettingBinding
import com.example.coin_monitoring.service.PriceForegroundService

class SettingActivity : AppCompatActivity() {

    private lateinit var binding : ActivitySettingBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.startForeground.setOnClickListener {
            Toast.makeText(this,"start",Toast.LENGTH_SHORT).show()
            val intent = Intent(this, PriceForegroundService::class.java)
            intent.action = "START"
            startService(intent)
        }
        binding.stopForeground.setOnClickListener {
            Toast.makeText(this,"stop",Toast.LENGTH_SHORT).show()
            val intent = Intent(this, PriceForegroundService::class.java)
            intent.action = "STOP"
            startService(intent)
        }

    }
}