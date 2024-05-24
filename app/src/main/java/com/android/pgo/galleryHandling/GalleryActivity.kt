package com.android.pgo.galleryHandling

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.pgo.DataHolder
import com.android.pgo.R

class GalleryActivity : AppCompatActivity() {
    private lateinit var videoAdapter: VideoAdapter
    private val videoPaths = ArrayList<String>()

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_gallery)

        // Inicializace RecyclerView a Adapter
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = GridLayoutManager(this, 3)

        videoAdapter = VideoAdapter(videoPaths) { videoPath ->
            val intent = Intent(this, FullScreenVideoActivity::class.java)
            intent.putExtra("VIDEO_PATH", videoPath)
            startActivity(intent)
        }
        recyclerView.adapter = videoAdapter

        //pridani cest do SINGLETON tridy Data Holder
        DataHolder.videoPaths?.let {
            videoPaths.addAll(it)
            videoAdapter.notifyDataSetChanged()
        }
    }
}