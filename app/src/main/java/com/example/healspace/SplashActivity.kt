package com.example.healspace

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import android.widget.ImageView

class SplashActivity : AppCompatActivity() {

    private lateinit var imageViewIcon: ImageView
    private lateinit var imageViewLogo: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)


        imageViewIcon = findViewById(R.id.imageViewIcon)
        imageViewLogo = findViewById(R.id.imageViewLogo)

        // Set initial visibility
        imageViewIcon.visibility = ImageView.VISIBLE
        imageViewLogo.visibility = ImageView.INVISIBLE


        imageViewIcon.startAnimation(AnimationUtils.loadAnimation(this, R.anim.scale_down))
        imageViewIcon.animation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation) {}
            override fun onAnimationEnd(animation: Animation) {

                imageViewLogo.visibility = ImageView.VISIBLE
                imageViewLogo.startAnimation(AnimationUtils.loadAnimation(
                    this@SplashActivity, R.anim.fade_in))
                imageViewLogo.animation.setAnimationListener(object : Animation.AnimationListener {
                    override fun onAnimationStart(animation: Animation) {}
                    override fun onAnimationEnd(animation: Animation) {

                        imageViewIcon.startAnimation(AnimationUtils.loadAnimation(
                            this@SplashActivity, R.anim.slide_out_right))
                        imageViewLogo.startAnimation(AnimationUtils.loadAnimation(
                            this@SplashActivity, R.anim.slide_out_left))

                        Handler(Looper.getMainLooper()).postDelayed({

                            imageViewIcon.visibility = ImageView.GONE
                            imageViewLogo.visibility = ImageView.GONE

                            startActivity(Intent(this@SplashActivity,
                                OnboardingActivity::class.java))
                            finish()
                        }, 800) // Delay should match the duration of the slide-out animations
                    }
                    override fun onAnimationRepeat(animation: Animation) {}
                })
            }
            override fun onAnimationRepeat(animation: Animation) {}
        })

    }

    override fun onDestroy() {
        super.onDestroy()
        imageViewIcon.clearAnimation()
        imageViewLogo.clearAnimation()
    }
}