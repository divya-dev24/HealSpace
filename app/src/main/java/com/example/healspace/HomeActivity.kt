package com.example.healspace

import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment

class HomeActivity : AppCompatActivity() {

    private lateinit var homeIcon: ImageView
    private lateinit var medIcon: ImageView
    private lateinit var moodIcon: ImageView
    private lateinit var profileIcon: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_home)

        // Initialize icons
        homeIcon = findViewById(R.id.home_icon)
        medIcon = findViewById(R.id.med_icon)
        moodIcon = findViewById(R.id.mood_icon)
        profileIcon = findViewById(R.id.profile_icon)

        // Set default fragment (HomeFragment)
        replaceFragment(HomeFragment())
        highlightIcon(homeIcon)

        // Set click listeners for navigation
        homeIcon.setOnClickListener {
            replaceFragment(HomeFragment())
            highlightIcon(homeIcon)
        }

        medIcon.setOnClickListener {
            replaceFragment(MedFragment())
            highlightIcon(medIcon)
        }

        moodIcon.setOnClickListener {
            replaceFragment(MoodFragment())
            highlightIcon(moodIcon)
        }

        profileIcon.setOnClickListener {
            replaceFragment(ProfileFragment())
            highlightIcon(profileIcon)
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    // Function to replace fragments
    private fun replaceFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, fragment)
        transaction.commit()
    }

    // Function to change icon colors when selected
    private fun highlightIcon(selectedIcon: ImageView) {
        val defaultColor = ContextCompat.getColor(this, R.color.gray)
        val selectedColor = ContextCompat.getColor(this, R.color.lesswhite)

        homeIcon.setColorFilter(defaultColor)
        medIcon.setColorFilter(defaultColor)
        moodIcon.setColorFilter(defaultColor)
        profileIcon.setColorFilter(defaultColor)

        selectedIcon.setColorFilter(selectedColor)
    }
}