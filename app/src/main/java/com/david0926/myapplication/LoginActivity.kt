package com.david0926.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Initialize Firebase Auth
        auth = Firebase.auth

        val etLoginId = findViewById<EditText>(R.id.et_login_id)
        val etLoginPw = findViewById<EditText>(R.id.et_login_pw)

        val btLoginRegister = findViewById<Button>(R.id.bt_login_register)
        val btLogin = findViewById<Button>(R.id.bt_login)

        btLogin.setOnClickListener { login(etLoginId.text.toString(), etLoginPw.text.toString()) }
        btLoginRegister.setOnClickListener { register() }
    }

    private fun login(id: String, pw: String) {

        if (id.isBlank() || pw.isBlank()){
            Toast.makeText(this, "빈칸을 모두 입력해주세요.", Toast.LENGTH_SHORT).show()
            return
        }

        auth.signInWithEmailAndPassword(id, pw)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    gotoMain()
                } else {
                    Toast.makeText(this, "로그인에 실패했습니다.", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun register(){
        startActivity(Intent(this, RegisterActivity::class.java))
        finish()
    }

    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        if(currentUser != null){
            gotoMain()
        }
    }

    private fun gotoMain(){
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}