package com.david0926.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class RegisterActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        auth = Firebase.auth
        db = Firebase.firestore

        val etRegisterName = findViewById<EditText>(R.id.et_register_name)
        val etRegisterId = findViewById<EditText>(R.id.et_register_id)
        val etRegisterPw = findViewById<EditText>(R.id.et_register_pw)

        val btRegister = findViewById<Button>(R.id.bt_register)

        btRegister.setOnClickListener {
            register(
                etRegisterName.text.toString(),
                etRegisterId.text.toString(),
                etRegisterPw.text.toString()
            )
        }
    }

    fun register(name: String, id: String, pw: String) {
        auth.createUserWithEmailAndPassword(id, pw)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Create a new user with a first and last name

                    val user = hashMapOf(
                        "name" to name,
                        "email" to id
                    )

                    // Add a new document with a generated ID
                    db.collection("users")
                        .document(id)
                        .set(user)
                        .addOnSuccessListener { documentReference ->
                            gotoMain()
                        }
                        .addOnFailureListener { e ->
                            Toast.makeText(this, "유저 정보 업로드에 실패했습니다.", Toast.LENGTH_SHORT).show()
                        }
                } else {
                    Toast.makeText(this, "회원가입에 실패했습니다.", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun gotoMain() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    override fun onBackPressed() {
        startActivity(Intent(this, LoginActivity::class.java))
        super.onBackPressed()
    }
}