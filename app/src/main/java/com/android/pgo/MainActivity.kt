package com.android.pgo

import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.view.animation.AnimationUtils
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

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

    }

}