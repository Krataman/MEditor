package com.android.pgo.galleryHandling

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.pgo.R

class GalleryActivity : AppCompatActivity() {
    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_gallery)

        lateinit var videoAdapter: VideoAdapter
        val videoPaths = mutableListOf<String>()

        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = GridLayoutManager(this, 3)

        videoAdapter = VideoAdapter(videoPaths)
        recyclerView.adapter = videoAdapter

        intent.getStringArrayListExtra("TRIMMED_VIDEO_PATHS")?.let {
            videoPaths.addAll(it)
            videoAdapter.notifyDataSetChanged()
        }
    }
}