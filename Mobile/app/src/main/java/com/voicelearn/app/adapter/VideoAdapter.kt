package com.voicelearn.app.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.voicelearn.app.databinding.ItemVideoBinding
import com.voicelearn.app.models.YouTubeVideo

class VideoAdapter(
    private val videos: List<YouTubeVideo>,
    private val onVideoClick: (YouTubeVideo) -> Unit
) : RecyclerView.Adapter<VideoAdapter.VideoViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoViewHolder {
        val binding = ItemVideoBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return VideoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: VideoViewHolder, position: Int) {
        holder.bind(videos[position])
    }

    override fun getItemCount() = videos.size

    inner class VideoViewHolder(private val binding: ItemVideoBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(video: YouTubeVideo) {
            binding.tvVideoTitle.text = video.title
            Glide.with(itemView.context)
                .load(video.thumbnailUrl)
                .centerCrop()
                .into(binding.ivVideoThumbnail)

            itemView.setOnClickListener { onVideoClick(video) }
        }
    }
}