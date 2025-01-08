package com.example.youtubeapp

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

class PlayerVideoAdapter: ListAdapter<VideoItem,PlayerVideoAdapter.ViewHolder>(diffUtil) {
    inner class ViewHolder(): RecyclerView.ViewHolder(){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

    }

    companion object{
        val diffUtil = object: DiffUtil.ItemCallback<VideoItem>(){
            override fun areItemsTheSame(oldItem: VideoItem, newItem: VideoItem): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: VideoItem, newItem: VideoItem): Boolean {
                return oldItem == newItem
            }

        }
    }
}