package com.android.pgo

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.android.pgo.galleryHandling.GalleryActivity

class MainActivity : AppCompatActivity() {

    //region onCreate
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        enableEdgeToEdge()
        initButtons()

    }
    //endregion
    //region initButtons
    private fun initButtons(){
        val button1 = findViewById<View>(R.id.viewGalleryButton)
        val button2 = findViewById<View>(R.id.trimVideoButton)
        val button3 = findViewById<View>(R.id.cropImagesButton)
        val dev = findViewById<View>(R.id.settings)

        //region button2
        button2.setOnClickListener(View.OnClickListener {
            val intent = Intent(this, SelectVideoTypeActivity::class.java)
            startActivity(intent)
        })
        //endregion
        //region butto1
        button1.setOnClickListener(View.OnClickListener {
            val i:Intent = intent
            val intent = Intent(this, GalleryActivity::class.java)
            intent.putStringArrayListExtra("TRIMMED_VIDEO_PATHS",
                i.getStringArrayListExtra("TRIMMED_VIDEO_PATHS")?.let { it1 -> ArrayList(it1) })
            startActivity(intent)
        })
        //endregion
        //region button3
        button3.setOnClickListener(View.OnClickListener {
            val intent = Intent(this, SelectImageTypeActivity::class.java)
            startActivity(intent)
        })
        //endregion
        //region dev
        dev.setOnClickListener(View.OnClickListener {
            val intent = Intent(this, SettingsActivity::class.java)
            startActivity(intent)
        })
        //endregion

    }
    //endregion

}