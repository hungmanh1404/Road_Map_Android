package com.example.road_map_android

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.road_map_android.Activity3.Companion.KEY_RESULT
import com.example.road_map_android.data.vo.User
import com.example.road_map_android.databinding.Activity2Binding
import com.google.android.material.bottomsheet.BottomSheetBehavior

class Activity2 : AppCompatActivity() {
    private var _binding: Activity2Binding? = null
    private val binding get() = _binding
    private var bottomSheetBehavior: BottomSheetBehavior<View>? = null
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
        setupBottomSheet()
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
        binding?.tvResultFromActivity3?.visibility = View.GONE
    }

    private fun onListenResultActivity3(result: ActivityResult) {
        result.data?.getStringExtra(KEY_RESULT)?.let {
            binding?.tvResultFromActivity3?.run {
                text = "Result from Activity3: $it"
                visibility = View.VISIBLE
            }
        }
    }

    private fun setupClickListeners() {
        binding?.run {
            btnGoToActivity3.setOnClickListener {
                openActivity3()
            }

            btnOpenBottomSheet.setOnClickListener {
                showBottomSheet()
            }

            btnBottomSheetAction1.setOnClickListener {
                hideBottomSheet()
            }

            btnBottomSheetAction2.setOnClickListener {
                hideBottomSheet()
            }

            binding?.btnBottomSheetAction3?.setOnClickListener {
                hideBottomSheet()
            }
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

    private fun setupBottomSheet() {
        binding?.bottomSheet?.let { bottomSheet ->
            bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet)
            bottomSheetBehavior?.apply {
                isHideable = true
                peekHeight = 0
                skipCollapsed = true
                state = BottomSheetBehavior.STATE_HIDDEN
            }
        }
    }

    private fun showBottomSheet() {
        bottomSheetBehavior?.state = BottomSheetBehavior.STATE_EXPANDED
    }

    private fun hideBottomSheet() {
        bottomSheetBehavior?.state = BottomSheetBehavior.STATE_HIDDEN
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
