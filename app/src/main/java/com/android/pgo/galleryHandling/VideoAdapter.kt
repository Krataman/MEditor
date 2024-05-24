package com.android.pgo.galleryHandling

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.VideoView
import androidx.recyclerview.widget.RecyclerView
import com.android.pgo.R

class VideoAdapter(
    private val videoPaths: List<String>,
    private val onVideoClick: (String) -> Unit // lambda funkce, volana pri kliku na video | UNIT -> nic nevraci / void v java || String je cesta k videu
):RecyclerView.Adapter<VideoAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.video_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val videoPath = videoPaths[position]
        holder.itemView.setOnClickListener {onVideoClick(videoPath)}
        holder.videoView.setVideoURI(Uri.parse(videoPath))
        holder.videoView.seekTo(1)
    }

    override fun getItemCount(): Int {
        return videoPaths.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val videoView: VideoView = view.findViewById(R.id.videoView)
    }
}