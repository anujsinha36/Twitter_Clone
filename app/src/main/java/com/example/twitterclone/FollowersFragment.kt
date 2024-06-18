package com.example.twitterclone

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.twitterclone.adapters.ClickListener
import com.example.twitterclone.adapters.FollowersAdapter
import com.example.twitterclone.data.SuggestedFollowers
import com.example.twitterclone.data.User
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class FollowersFragment : Fragment(), ClickListener {
    private lateinit var rvSuggestedFollowers : RecyclerView
    private lateinit var listOfFollowers : MutableList<SuggestedFollowers>
    private lateinit var followersAdapter: FollowersAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.activity_followers, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rvSuggestedFollowers = view.findViewById(R.id.rv_suggested_followers)
        listOfFollowers = mutableListOf()


        FirebaseDatabase.getInstance().reference.child("users")
            .child(Firebase.auth.uid.toString()).addListenerForSingleValueEvent(object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    val followerList = snapshot.child("followersList").value as MutableList<String>

                    FirebaseDatabase.getInstance().reference.child("users").
                    addListenerForSingleValueEvent(object : ValueEventListener{
                        override fun onDataChange(snapshot: DataSnapshot) {
                            for (dataSnapshot in snapshot.children){
                                val user = dataSnapshot.getValue(User::class.java)
                                if (user?.uid.toString() !=Firebase.auth.uid.toString()  && !followerList.contains(user?.uid.toString())){
                                    val finalSuggestedAccount = SuggestedFollowers(
                                        suggestedAccountUID = user?.uid.toString(),
                                        suggestedEmail = user?.userEmail.toString(),
                                        suggestedImage = user?.userProfilePicture.toString()
                                    )
                                    listOfFollowers.add(finalSuggestedAccount)
                                    followersAdapter = FollowersAdapter(listOfFollowers,requireContext(),this@FollowersFragment)
                                    rvSuggestedFollowers.adapter = followersAdapter
                                    rvSuggestedFollowers.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL,false)
                                }
                            }
                        }

                        override fun onCancelled(error: DatabaseError) {
                            //TODO("Not yet implemented")
                        }

                    })

                }

                override fun onCancelled(error: DatabaseError) {
                   // TODO("Not yet implemented")
                }

            })

    }

private fun onClicked(uid: String){
    FirebaseDatabase.getInstance().reference.child("users").child(Firebase.auth.uid.toString()).
    addListenerForSingleValueEvent(object : ValueEventListener {
        override fun onDataChange(snapshot: DataSnapshot) {
            val followerList = snapshot.child("followersList").value as MutableList<String>
            followerList.add(uid)

            FirebaseDatabase.getInstance().reference.child("users").child(Firebase.auth.uid.toString()).
            child("followersList").setValue(followerList)

            Toast.makeText(requireContext(), "Followed Successfully", Toast.LENGTH_SHORT).show()
        }

        override fun onCancelled(error: DatabaseError) {
            //TODO("Not yet implemented")
        }
    }
    )
}

    override fun onClickListener(uid: String) {
        onClicked(uid)
    }

}

