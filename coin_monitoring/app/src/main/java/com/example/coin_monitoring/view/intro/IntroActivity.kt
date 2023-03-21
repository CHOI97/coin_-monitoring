package com.example.coin_monitoring.view.intro

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.Observer
import com.example.coin_monitoring.MainActivity
import com.example.coin_monitoring.R
import timber.log.Timber

// Splash 화면 만들기
// handler -> 3초 뒤에 다른 액티비티 ex) MainActivity 로 이동
// https://developer.android.com/develop/ui/views/launch/splash-screen

class IntroActivity : AppCompatActivity() {

    private val viewModel : IntroViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {

        installSplashScreen()

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro)

        Timber.d("onCreate")

        viewModel.checkFirstFlag()
        viewModel.first.observe(this, Observer{
            if(it){
                // 처음 접속하는 유저가 아님
                val intent = Intent(this,MainActivity::class.java)
                startActivity(intent)
            }else{
                // 처음 접속하는 유저
            }
        })
    }
}