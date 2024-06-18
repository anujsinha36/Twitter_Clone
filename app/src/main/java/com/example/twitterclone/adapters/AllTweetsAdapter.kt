package com.example.twitterclone.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.twitterclone.R
import com.example.twitterclone.data.AllTweets

class AllTweetsAdapter(
     val listOfAllTweets : List<AllTweets> ):
    RecyclerView.Adapter<AllTweetsAdapter.AllTweetsViewHolder>() {


    class AllTweetsViewHolder(view : View) : RecyclerView.ViewHolder(view){
        val allTweets : TextView = view.findViewById(R.id.text_all_tweets)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AllTweetsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.layout_rv_tweets, parent, false)
        return AllTweetsViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listOfAllTweets.size
    }

    override fun onBindViewHolder(holder: AllTweetsViewHolder, position: Int) {
        val currentTweet = listOfAllTweets[position]
        holder.allTweets.text = currentTweet.tweets
    }

}
