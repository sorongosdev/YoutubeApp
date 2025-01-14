package com.example.youtubeapp

import com.google.gson.annotations.SerializedName

data class VideoList(
    val videos: List<VideoEntity>
)

data class VideoEntity(
    @SerializedName("id")
    val id: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("sources")
    val videoUrl: String,
    @SerializedName("channelName")
    val channelName: String,
    @SerializedName("viewCount")
    val viewCount: String,
    @SerializedName("dateText")
    val dateText: String,
    @SerializedName("channelThumb")
    val channelThumb: String,
    @SerializedName("thumb") // json에서 이 키로 불러와서
    val videoThumb: String, // 이 닉네임으로 사용해
)
