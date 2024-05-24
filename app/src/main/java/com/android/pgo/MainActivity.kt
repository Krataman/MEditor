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

    private fun initButtons(){
        val button1 = findViewById<View>(R.id.viewGalleryButton)
        val button2 = findViewById<View>(R.id.trimVideoButton)

        //region button2
        button2.setOnClickListener(View.OnClickListener {
            val intent = Intent(this, SelectOptionActivity::class.java)
            startActivity(intent)
        })
        //endregion

        button1.setOnClickListener(View.OnClickListener {
            val i:Intent = intent
            val intent = Intent(this, GalleryActivity::class.java)
            intent.putStringArrayListExtra("TRIMMED_VIDEO_PATHS",
                i.getStringArrayListExtra("TRIMMED_VIDEO_PATHS")?.let { it1 -> ArrayList(it1) })
            startActivity(intent)
        })

    }

}