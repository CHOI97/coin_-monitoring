package com.example.coin_monitoring.view.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.coin_monitoring.R
import com.example.coin_monitoring.databinding.FragmentPriceChangeBinding

class PriceChangeFragment : Fragment() {

    private var _binding: FragmentPriceChangeBinding? = null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentPriceChangeBinding.inflate(inflater,container,false)
        val view = binding.root
        return view
    }
}