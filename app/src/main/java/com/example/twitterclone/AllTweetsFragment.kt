package com.example.twitterclone

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.twitterclone.adapters.AllTweetsAdapter
import com.example.twitterclone.data.AllTweets
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class AllTweetsFragment : Fragment() {
    private lateinit var rvAllTweets : RecyclerView
    private lateinit var allTweetsAdapter: AllTweetsAdapter
    private lateinit var allTweetList: MutableList<AllTweets>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.activity_alltweets,container,false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rvAllTweets = view.findViewById(R.id.rv_all_tweets)
        allTweetList = mutableListOf()
        allTweetsAdapter = AllTweetsAdapter(allTweetList)
        rvAllTweets.adapter = allTweetsAdapter
        rvAllTweets.layoutManager = LinearLayoutManager(requireContext())

        FirebaseDatabase.getInstance().reference.child("user").child(Firebase.auth.uid.toString())
            .addListenerForSingleValueEvent(object :ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    var followerListUID = mutableListOf<String>()
                        snapshot.child("followersList").value?.let {
                            followerListUID = it as MutableList<String>
                            followerListUID.add(Firebase.auth.uid.toString())
                        }
                    followerListUID.forEach {
                        getTweets(it)
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                   // TODO("Not yet implemented")
                }

            })

    }

    private fun getTweets(uID: String){
        FirebaseDatabase.getInstance().reference.child("user").child(uID)
            .addListenerForSingleValueEvent(object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    var tweetList = mutableListOf<String>()
                        snapshot.child("tweetList").value?.let {
                            tweetList = it as MutableList<String>
                    }
                    tweetList.forEach {
                        if (!it.isNullOrBlank()){
                            allTweetList.add(AllTweets(it))
                        }
                    }
                    allTweetsAdapter = AllTweetsAdapter(allTweetList)
                    rvAllTweets.adapter = allTweetsAdapter
                    rvAllTweets.layoutManager = LinearLayoutManager(requireContext())

                }

                override fun onCancelled(error: DatabaseError) {
                    //TODO("Not yet implemented")
                }

            })
    }
}
