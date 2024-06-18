package com.example.twitterclone

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.twitterclone.data.User
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.database.database

class LoginActivity : AppCompatActivity() {
    private lateinit var loginText : TextView
    private lateinit var edtEmail : EditText
    private lateinit var edtPassword : EditText
    private lateinit var btnLogin : Button
    private lateinit var btnSignUp : Button
    private lateinit var edtName : EditText
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)

        loginText = findViewById(R.id.txt_head)
        edtEmail = findViewById(R.id.edt_email)
        edtPassword = findViewById(R.id.edt_password)
        btnLogin = findViewById(R.id.btn_login)
        btnSignUp = findViewById(R.id.btn_signup)
        edtName = findViewById(R.id.edt_name)
        auth = Firebase.auth

        if (auth.currentUser != null){
            val intent = Intent(this, HomeScreenActivity::class.java)
            startActivity(intent)
            finish()
        }


        btnLogin.setOnClickListener{
            val email = edtEmail.text.toString()
            val password = edtPassword.text.toString()
            login(email, password)
        }



        btnSignUp.setOnClickListener{
            val email = edtEmail.text.toString()
            val password = edtPassword.text.toString()
            val name = edtName.text.toString()
            signUp(email, password, name)

        }


    }
    private fun addUserData(user: User){
        Firebase.database.getReference("users").child(user.uid).setValue(user)
    }

    private fun login(email : String,  password: String){
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val intent = Intent(this, HomeScreenActivity::class.java)
                    startActivity(intent)
                    finish()
                    Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show()


                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show()
                }
            }

    }
    private fun signUp(email : String,  password: String, name : String){
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    var followersList  = mutableListOf<String>()
                    followersList.add("")

                    var tweetsList  = mutableListOf<String>()
                    tweetsList.add("")
                    val user = User(
                        userEmail = email,
                        userProfilePicture = "",
                        uid = auth.uid.toString(),
                        followersList = followersList,
                        tweetList = tweetsList,
                        name = name
                    )
                    addUserData(user)
                    val intent = Intent(this, HomeScreenActivity::class.java)
                    startActivity(intent)
                    finish()
                    Toast.makeText(this, "Sign Up completed", Toast.LENGTH_SHORT).show()


                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show()
                }
            }

    }

}