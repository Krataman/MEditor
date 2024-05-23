package com.android.pgo

import android.net.Uri
import android.os.Bundle
import android.widget.VideoView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class TrimVideoActivity : AppCompatActivity() {

    private lateinit var videoView: VideoView
    private lateinit var videoUri: Uri

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_trim_video)
        enableEdgeToEdge()

        videoView = findViewById(R.id.videoView)
        val uriString = intent.getStringExtra("VIDEO_URI")

        if (uriString != null) {
            videoUri = Uri.parse(uriString) // parse do Uri
            videoView.setVideoURI(videoUri)
            videoView.start()
        }
    }
}
