package com.example.twitterclone.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.twitterclone.FollowersFragment
import com.example.twitterclone.R
import com.example.twitterclone.data.SuggestedFollowers
import com.google.firebase.database.core.Context
import de.hdodenhof.circleimageview.CircleImageView

class FollowersAdapter(
    val followersList: List<SuggestedFollowers>,
    private val context : android.content.Context,
    private val clickListener: ClickListener
) : RecyclerView.Adapter<FollowersAdapter.FollowersViewHolder>() {

    class FollowersViewHolder(view: View): RecyclerView.ViewHolder(view){
        val suggestedProfileImage : CircleImageView = view.findViewById(R.id.suggested_profile_image)
        val textEmail : TextView = view.findViewById(R.id.txt_email)
        val btnFollow : Button = view.findViewById(R.id.btn_follow)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FollowersViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.layout_rv_followers, parent, false)
        return FollowersViewHolder(view)
    }

    override fun getItemCount(): Int {
        return followersList.size
    }

    override fun onBindViewHolder(holder: FollowersViewHolder, position: Int) {
        val currentAccount = followersList[position]
        holder.textEmail.text = currentAccount.suggestedEmail
        Glide.with(context).load(currentAccount.suggestedImage)
            .into(holder.suggestedProfileImage)
        holder.btnFollow.setOnClickListener {
            clickListener.onClickListener(currentAccount.suggestedAccountUID)
        }
    }
}

interface ClickListener{
    fun onClickListener(uid : String)
}