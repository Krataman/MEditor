package com.android.pgo.galleryHandling

import android.net.Uri
import android.os.Bundle
import android.widget.ImageView
import android.widget.VideoView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.android.pgo.R

class FullScreenImageActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_full_screen_image)
        displayImage()
    }

    private fun displayImage(){
        val imageView:ImageView = findViewById(R.id.imageView)
        val imagePath = intent.getStringExtra("MEDIA_PATH")
        val uri = Uri.parse(imagePath)

        imageView.setImageURI(uri)
    }
}