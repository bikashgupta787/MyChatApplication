package com.example.chatapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract.CommonDataKinds.Email
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    private lateinit var pswdTv: EditText
    private lateinit var nameTv: EditText
    private lateinit var login: Button
    private lateinit var signup: Button
    private lateinit var mAuth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        supportActionBar?.hide()

        mAuth = FirebaseAuth.getInstance()
        pswdTv = findViewById(R.id.pswdTv)
        nameTv = findViewById(R.id.nameTv)
        login = findViewById(R.id.buttonLogin)
        signup = findViewById(R.id.buttonSignUp)

        signup.setOnClickListener {
            val intent = Intent(this,SignupActivity::class.java)
            startActivity(intent)
        }

        login.setOnClickListener {
            val email = nameTv.text.toString()
            val pswd = pswdTv.text.toString()

            login(email,pswd)
            //email - bikash@gmail.com,akash@gmail.com,rahul@gmail.com
            //pswd - bikash123
        }
    }

    private fun login(email: String, password: String){
        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val intent = Intent(this,MainActivity::class.java)
                    finish()
                    startActivity(intent)
                }
                else {
                    Toast.makeText(this,"User doesnt exists", Toast.LENGTH_SHORT).show()
                }
            }
    }
}