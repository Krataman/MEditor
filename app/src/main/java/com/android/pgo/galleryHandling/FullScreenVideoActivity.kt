package com.android.pgo.galleryHandling

import android.net.Uri
import android.os.Bundle
import android.widget.MediaController
import android.widget.VideoView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.android.pgo.R

class FullScreenVideoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_full_screen_video)

        displayVideo()
    }

    private fun displayVideo(){
        val videoView = findViewById<VideoView>(R.id.videoView)
        val videoPath = intent.getStringExtra("VIDEO_PATH")
        val uri = Uri.parse(videoPath)

        videoView.setVideoURI(uri)

        val mediaController = MediaController(this)
        videoView.setMediaController(mediaController)
        mediaController.setAnchorView(videoView)

        videoView.start()
    }
}