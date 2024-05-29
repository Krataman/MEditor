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
    private lateinit var videoAdapter: MediaAdapter
    private val mediaPaths = ArrayList<String>()
    private var b = true

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_gallery)

        if(DataHolder.load){
            DataHolder.onLoadAddMediaPaths()
            DataHolder.load = false
        }

        // Inicializace RecyclerView a Adapter
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = GridLayoutManager(this, 4)

        val adapter = MediaAdapter(this, DataHolder.mediaPaths) { mediaPath ->
            //Video nebo obrazek

            if (mediaPath.endsWith(".mp4")) {
                val intent = Intent(this, FullScreenVideoActivity::class.java)
                intent.putExtra("MEDIA_PATH", mediaPath)
                startActivity(intent)
            } else {
                // OBRAZEK
                val intent = Intent(this, FullScreenImageActivity::class.java)
                intent.putExtra("MEDIA_PATH", mediaPath)
                startActivity(intent)
            }
        }
        recyclerView.adapter = adapter


    }
}