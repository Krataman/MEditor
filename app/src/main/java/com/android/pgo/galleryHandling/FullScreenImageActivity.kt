package com.android.pgo.galleryHandling

import android.os.Bundle
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.android.pgo.R
import com.bumptech.glide.Glide

class FullScreenImageActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_full_screen_image)
        displayImage()
    }

    private fun displayImage(){
        val imageView: ImageView = findViewById(R.id.imageView)
        val imagePath = intent.getStringExtra("MEDIA_PATH")

        Glide.with(this)
            .load(imagePath)
            .into(imageView)
    }
}