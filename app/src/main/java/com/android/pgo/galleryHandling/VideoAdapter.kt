package com.android.pgo.galleryHandling

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.VideoView
import androidx.recyclerview.widget.RecyclerView
import com.android.pgo.R

class VideoAdapter(
    private val videoPaths: List<String>
):RecyclerView.Adapter<VideoAdapter.VideoViewHolder>() {

    class VideoViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val videoView: VideoView = view.findViewById(R.id.videoView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.video_item, parent, false)
        return VideoViewHolder(view)
    }

    override fun onBindViewHolder(holder: VideoViewHolder, position: Int) {
        val videoPath = videoPaths[position]
        holder.videoView.setVideoURI(Uri.parse(videoPath))
        holder.videoView.seekTo(1)
    }

    override fun getItemCount(): Int {
        return videoPaths.size
    }
}