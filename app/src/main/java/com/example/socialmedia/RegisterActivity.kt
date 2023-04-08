package com.example.socialmedia

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class RegisterActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var email:EditText
    private lateinit var pass:EditText
    private lateinit var confirmPass:EditText
    private lateinit var progressbar:ProgressBar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        email = findViewById<EditText>(R.id.register_email)
        pass = findViewById<EditText>(R.id.register_password)
        confirmPass = findViewById<EditText>(R.id.register_confirmpassword)
        val checkbox = findViewById<CheckBox>(R.id.register_checkbox)
        val loginBtn = findViewById<Button>(R.id.register_to_login)
        val registerBtn = findViewById<Button>(R.id.button_register)
        progressbar = findViewById<ProgressBar>(R.id.progressbar_register)
//        Toast.makeText(this,"register btn clicked",Toast.LENGTH_LONG).show()
        registerBtn.setOnClickListener {
//            Toast.makeText(this,"register btn clicked",Toast.LENGTH_LONG).show()
            progressbar.visibility = View.VISIBLE
            createAccount()
        }
        checkbox.setOnClickListener() {
            if(checkbox.isChecked) {
                pass.inputType = 1
                confirmPass.inputType = 1
            }
            else {
                pass.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                confirmPass.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
            }
        }
        loginBtn.setOnClickListener() {
            val intent  = Intent(this,LoginActivity::class.java)
            startActivity(intent)
        }
    }
    private fun createAccount() {
        val emailStr = email.text.toString()
        val passStr = pass.text.toString()
        val confirmPassStr = confirmPass.text.toString()
        if(passStr == confirmPassStr) {
            when {
                TextUtils.isEmpty(emailStr) -> Toast.makeText(this, "enter email", Toast.LENGTH_LONG).show()
                TextUtils.isEmpty(passStr) -> Toast.makeText(this, "enter password", Toast.LENGTH_LONG).show()
                TextUtils.isEmpty(confirmPassStr) -> Toast.makeText(this, "enter confirm password", Toast.LENGTH_LONG).show()

                else -> {
                    auth = FirebaseAuth.getInstance()
                    auth.createUserWithEmailAndPassword(emailStr, passStr).addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(this, "successful", Toast.LENGTH_LONG).show()
                            saveInfo(emailStr, passStr)
                            val intent  = Intent(this,MainActivity::class.java)
                            startActivity(intent)

                        } else {
                            val msg = task.exception!!.toString()
                            Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
                            progressbar.visibility = View.INVISIBLE
                        }
                    }
                }
            }
        }
        else {
            Toast.makeText(this,"confirm password doesn't match",Toast.LENGTH_LONG).show()
        }
    }
    private fun saveInfo(emailStr : String,passStr:String) {
        val currentUserId = FirebaseAuth.getInstance().currentUser!!.uid
        val userRef : DatabaseReference = FirebaseDatabase.getInstance().reference.child("Users")
    }


}
