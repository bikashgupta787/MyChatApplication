package com.example.chatapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlin.math.sign

class SignupActivity : AppCompatActivity() {

    private lateinit var pswdTv: EditText
    private lateinit var nameTv: EditText
    private lateinit var signup: Button
    private lateinit var email: EditText
    private lateinit var mAuth : FirebaseAuth
    private lateinit var mDbRef :DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        supportActionBar?.hide()
        mAuth = FirebaseAuth.getInstance()
        pswdTv = findViewById(R.id.pswdTv)
        nameTv = findViewById(R.id.nameTv)
        email = findViewById(R.id.emailTv)
        signup = findViewById(R.id.buttonSignUp)

        signup.setOnClickListener {
            val email = email.text.toString()
            val password = pswdTv.text.toString()
            val name = nameTv.text.toString()

            signUp(name,email,password)
        }
    }

    private fun signUp(name:String, email: String,password: String){
        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    addUserToDb(name,email,mAuth.currentUser?.uid!!)
                    val intent = Intent(this,LoginActivity::class.java)
                    startActivity(intent)
                } else {
                    Toast.makeText(this,"Error",Toast.LENGTH_SHORT).show()
                }
            }
    }

   private fun addUserToDb(name: String,email: String,uid:String){
       mDbRef = FirebaseDatabase.getInstance().getReference()
       mDbRef.child("user").child(uid).setValue(UserModel(name,email,uid))
   }
}