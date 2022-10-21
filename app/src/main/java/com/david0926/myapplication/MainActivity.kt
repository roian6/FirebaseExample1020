package com.david0926.myapplication

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        auth = Firebase.auth

        val textView = findViewById<TextView>(R.id.tv_main_username)

        if (auth.currentUser != null) {
            textView.text = auth.currentUser?.email
        }

        val btLogout = findViewById<TextView>(R.id.bt_main_logout)
        btLogout.setOnClickListener {
            auth.signOut()
            finish()
        }
    }
}