package com.example.road_map_android

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
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
        binding?.webViewContainer?.visibility = View.GONE
    }

    private fun setupClickListeners() {
        binding?.run {
            btnGoToActivity2.setOnClickListener {
                val intent = Intent(root.context, Activity2::class.java)
                intent.putExtra("message", "Hello Activity2")
                intent.putExtra("user", User("Manh Nguyen", 27))
                startActivity(intent)
            }

            btnOpenWebView.setOnClickListener {
                webViewContainer.visibility = View.VISIBLE
                webView.apply {
                    webViewClient = WebViewClient()
                    settings.javaScriptEnabled = true
                    loadUrl("https://www.google.com")
                }
            }

            btnCloseWebView.setOnClickListener {
                webViewContainer.visibility = View.GONE
            }
        }
    }
}
