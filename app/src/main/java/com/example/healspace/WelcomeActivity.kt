package com.example.healspace

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class WelcomeActivity : AppCompatActivity() {

    private lateinit var btnToLogin: Button
    private lateinit var btnToSignUp: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_welcome)

        btnToLogin = findViewById(R.id.btn_to_login)
        btnToSignUp = findViewById(R.id.btn_to_sign_up)

        btnToLogin.setOnClickListener {

            val intent = Intent(this@WelcomeActivity, LogInActivity::class.java)
            startActivity(intent)

        }

        btnToSignUp.setOnClickListener {

            val intent = Intent(this@WelcomeActivity, SignUpActivity::class.java)
            startActivity(intent)

        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

    }
}