package com.example.twitterclone

import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.getValue

class TweetsActivity : AppCompatActivity() {
    private lateinit var edtTweet : EditText
    private lateinit var btnUploadTweet : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_tweets)

        edtTweet = findViewById(R.id.edt_tweet)
        btnUploadTweet = findViewById(R.id.btn_upload_tweet)

        btnUploadTweet.setOnClickListener {
             val userTweet = edtTweet.text.toString()
             getTweetListFromDatabase(userTweet)
            edtTweet.onEditorAction(EditorInfo.IME_ACTION_DONE)
        }

    }

    private fun getTweetListFromDatabase(userTweet : String){
        FirebaseDatabase.getInstance().reference.child("users").
        child(Firebase.auth.uid.toString()).addListenerForSingleValueEvent(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val tweetsList = snapshot.child("tweetList").value as MutableList<String>
                tweetsList.add(userTweet)
                uploadTweet(tweetsList)
            }

            override fun onCancelled(error: DatabaseError) {
                // TODO("Not yet implemented")
            }

        })
    }

    private fun uploadTweet(tweetsList: MutableList<String>){
        FirebaseDatabase.getInstance().reference.child("users").child(Firebase.auth.uid.toString()).
                child("tweetList").setValue(tweetsList)
        Toast.makeText(this, "Tweet Uploaded Successfully", Toast.LENGTH_SHORT).show()
    }
}