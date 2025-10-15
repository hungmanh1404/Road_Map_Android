package com.example.road_map_android

import android.content.Intent
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.road_map_android.databinding.Activity2Binding

class Activity2 : AppCompatActivity() {
    private var _binding: Activity2Binding? = null
    private val binding get() = _binding

    private val activityResultLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val data = result.data
            val resultFromActivity3 = data?.getStringExtra("result_from_activity3")
            resultFromActivity3?.let {
                binding?.tvResultFromActivity3?.run {
                    text = "Result from Activity3: $it"
                    visibility = android.view.View.VISIBLE
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = Activity2Binding.inflate(layoutInflater)
        setContentView(binding?.root)

        initViews()
        setupClickListeners()
        receiveDataFromActivity1()
    }

    private fun initViews() {
        // Ẩn TextView kết quả ban đầu
        binding?.tvResultFromActivity3?.visibility = android.view.View.GONE
    }

    private fun setupClickListeners() {
        binding?.btnGoToActivity3?.setOnClickListener {
            val intent = Intent(this, Activity3::class.java)
            activityResultLauncher.launch(intent)
        }
    }

    private fun receiveDataFromActivity1() {
        // Nhận String từ Activity1
        binding?.run {
            val message = intent.getStringExtra("message")
            tvMessage.text = "Message from Activity1: $message"

            // Nhận User object từ Activity1
            val user = intent.getParcelableExtra<User>("user")
            user?.let {
                tvUserInfo.text = "User Info:\nName: ${it.name}\nAge: ${it.age}"
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
