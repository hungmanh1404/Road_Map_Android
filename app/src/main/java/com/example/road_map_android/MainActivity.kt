package com.example.road_map_android

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import com.example.road_map_android.data.vo.User
import com.example.road_map_android.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        initViews()
        setupClickListeners()
    }

    private fun initViews() {
        visibilityWebView(View.GONE)
    }

    private fun setupClickListeners() {
        binding?.run {
            btnGoToActivity2.setOnClickListener {
                openActivity2()
            }

            btnOpenWebView.setOnClickListener {
                openWebView("https://www.google.com")
            }

            btnCloseWebView.setOnClickListener {
                visibilityWebView(View.GONE)
            }
        }
    }

    private fun visibilityWebView(state: Int) {
        binding?.run {
            btnCloseWebView.visibility = state
            webView.visibility = state
        }
    }

    private fun openWebView(url: String) {
        visibilityWebView(View.VISIBLE)
        binding?.webView?.apply {
            webViewClient = WebViewClient()
            settings.javaScriptEnabled = true
            loadUrl(url)
        }
    }

    private fun openActivity2() {
        Activity2.start(
            context = this,
            message = "Hello Activity 2",
            user = User("Manh Nguyen", 25)
        )
    }

    companion object {
        internal fun start(context: Context) {
            Intent(context, MainActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
                context.startActivity(this)
            }
        }
    }
}
