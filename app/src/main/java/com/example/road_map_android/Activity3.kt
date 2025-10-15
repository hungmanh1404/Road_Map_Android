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

    private fun initViews() {}

    private fun setupClickListeners() {
        binding?.run {
            btnSendToActivity2.setOnClickListener {
                val intent = Intent()
                intent.putExtra("result_from_activity3", "Data from Activity3: Success!")
                setResult(RESULT_OK, intent)
                finish()
            }

            btnGoToActivity1.setOnClickListener {
                val intent = Intent(root.context, MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
                startActivity(intent)
            }
        }
    }
}
