package com.kaibltdinc.melancholian

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.airbnb.lottie.LottieAnimationView
import com.airbnb.lottie.LottieDrawable
import com.kaibltdinc.melancholian.databinding.ActivityMainBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: MyViewModel by viewModel()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val ani: LottieAnimationView = findViewById(R.id.download)
        ani.apply {
            setAnimation("loa.json")
            repeatCount = LottieDrawable.INFINITE
            playAnimation()
        }
        viewModel.setHos()
        viewModel.openGameFragment.observe(this) { shouldOpenGame ->
            if (shouldOpenGame) {
                openGameActivity()
            }
        }

        viewModel.openWebView.observe(this) { shouldOpenWebView ->
            if (shouldOpenWebView) {
                openWebViewActivity()
            }
        }
    }

    private fun openGameActivity() {
        val intent = Intent(this, Game::class.java)
        startActivity(intent)
        finish()
    }

    private fun openWebViewActivity() {

            val intent = Intent(this, Wera::class.java)
            startActivity(intent)
        finish()
    }
}