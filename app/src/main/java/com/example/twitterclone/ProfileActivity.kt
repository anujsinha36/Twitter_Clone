package com.example.twitterclone

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
import com.google.firebase.database.getValue
import com.google.firebase.storage.FirebaseStorage
import de.hdodenhof.circleimageview.CircleImageView
import java.util.UUID

class ProfileActivity : AppCompatActivity() {
    private lateinit var profileImage : CircleImageView
    private lateinit var imageButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_profile)

        profileImage = findViewById(R.id.profile_image)
        imageButton = findViewById(R.id.img_btn)

        init()

        imageButton.setOnClickListener {
            val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(galleryIntent, 101)
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 101 && resultCode == RESULT_OK){
            profileImage.setImageURI(data?.data)
            uploadImage(data?.data)
        }
    }

    private fun uploadImage(uri : Uri?){
        val imageName = UUID.randomUUID().toString() + ".jpg"
        val storageRef = FirebaseStorage.getInstance().reference.child("profileImages/$imageName")

        storageRef.putFile(uri!!).addOnSuccessListener {
            val imageLink = it.metadata?.reference?.downloadUrl
            imageLink?.addOnSuccessListener {
                FirebaseDatabase.getInstance().reference.child("users").child(Firebase.auth.uid.toString()).
                child("userProfilePicture").setValue(it.toString())
            }
        }

    }

    private fun init(){
        FirebaseDatabase.getInstance().reference.child("users").child(Firebase.auth.uid.toString()).
        addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val link = snapshot.child("userProfilePicture").value.toString()

                if (!link.isNullOrBlank()){
                    Glide.with(this@ProfileActivity).load(link).into(profileImage)
                }
                else{
                    profileImage.setImageResource(R.drawable.images_profile)
                }

            }

            override fun onCancelled(error: DatabaseError) {
                //TODO("Not yet implemented")
            }

        } )

    }

    }
