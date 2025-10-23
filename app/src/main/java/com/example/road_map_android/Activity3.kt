package com.example.road_map_android

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.road_map_android.databinding.Activity3Binding

class Activity3 : AppCompatActivity() {
    private var _binding: Activity3Binding? = null
    private val binding get() = _binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = Activity3Binding.inflate(layoutInflater)
        setContentView(binding?.root)

        initViews()
        setupClickListeners()
    }

    private fun initViews() {
        binding?.btnSendToActivity2?.isEnabled = false
    }

    private fun setupClickListeners() {
        binding?.run {
            btnSendToActivity2.setOnClickListener {
                sendDataToActivity2()
            }

            btnGoToActivity1.setOnClickListener {
                MainActivity.start(context = this@Activity3)
            }
        }
    }

    private fun sendDataToActivity2() {
        Intent().apply {
            putExtra(KEY_RESULT, "Data from Activity3: Success!")
            setResult(RESULT_OK, this)
            finish()
        }
    }

    companion object {
        internal const val KEY_RESULT = "result_from_activity3"
    }
}
