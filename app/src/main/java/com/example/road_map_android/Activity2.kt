package com.example.road_map_android

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.road_map_android.Activity3.Companion.KEY_RESULT
import com.example.road_map_android.data.vo.User
import com.example.road_map_android.databinding.Activity2Binding

class Activity2 : AppCompatActivity() {
    private var _binding: Activity2Binding? = null
    private val binding get() = _binding

    private val activityResultLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            onListenResultActivity3(result)
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

    override fun onRestart() {
        super.onRestart()
        binding?.tvTitle?.text = "Activity 2222"
    }

    override fun onPause() {
        super.onPause()
        Log.d("vaoday", "onPause")
        binding?.tvTitle?.text = "Activity 9999"
    }

    private fun initViews() {
        binding?.tvResultFromActivity3?.visibility = android.view.View.GONE
    }

    private fun onListenResultActivity3(result: ActivityResult) {
        result.data?.getStringExtra(KEY_RESULT)?.let {
            binding?.tvResultFromActivity3?.run {
                text = "Result from Activity3: $it"
                visibility = android.view.View.VISIBLE
            }
        }
    }

    private fun setupClickListeners() {
        binding?.btnGoToActivity3?.setOnClickListener {
            openActivity3()
        }
    }

    private fun receiveDataFromActivity1() {
        binding?.run {
            intent.getStringExtra(KEY_MESSAGE)?.run {
                tvMessage.text = "Message from Activity1: $this"
            }

            intent.getParcelableExtra<User>(KEY_USER)?.run {
                tvUserInfo.text = getString(R.string.user_info, name, age)
            }
        }
    }

    private fun openActivity3() {
        Intent(this, Activity3::class.java).apply {
            activityResultLauncher.launch(this)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {

        private const val KEY_MESSAGE = "message"
        private const val KEY_USER = "user"

        internal fun start(context: Context, message: String, user: User) {
            Intent(context, Activity2::class.java).apply {
                putExtra(KEY_MESSAGE, message)
                putExtra(KEY_USER, user)
                context.startActivity(this)
            }
        }
    }
}
