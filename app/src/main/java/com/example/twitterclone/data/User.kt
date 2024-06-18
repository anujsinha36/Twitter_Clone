package com.example.twitterclone.data

data class User(
    val userEmail : String = "",
    val userProfilePicture: String = "",
    val followersList: List<String> = listOf(),
    val tweetList : List<String> = listOf(),
    val uid : String = "",
    val name : String = ""

)
