package com.android.pgo

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import java.io.File

class SettingsActivity : AppCompatActivity() {
    private val TAG = "!!!!! Trimmed Files !!!!!"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_settings)

        initBut()
    }

    private fun initBut(){
        val button: Button = findViewById(R.id.deleteVIDS)
        button.setOnClickListener(View.OnClickListener {
            deleteVideos()
        })

        val button4:Button = findViewById(R.id.deleteIMAGES)
        button4.setOnClickListener(View.OnClickListener {
            deleteImages()
        })
    }
    //region delete VIDS
    private fun deleteVideos() {
        val directoryPath = "/storage/emulated/0/Android/data/com.android.pgo/files/TrimmedVideo/"
        val directory = File(directoryPath)

        if (directory.exists() && directory.isDirectory) {
            val files = directory.listFiles()
            if (files != null) {
                for (file in files) {
                    if (file.delete()) {
                        Log.d(TAG, "Deleted file: ${file.name}")
                    } else {
                        Log.d(TAG, "Failed to delete file: ${file.name}")
                    }
                }
            } else {
                Log.e(TAG, "No files found in the directory.")
            }
        } else {
            Log.e(TAG, "Directory does not exist or is not a directory.")
        }
    }
    //endregion
    //region delete imgs
    private fun deleteImages() {
        val directoryPath = "/storage/emulated/0/Android/data/com.android.pgo/files/Pictures/"
        val directory = File(directoryPath)

        if (directory.exists() && directory.isDirectory) {
            val files = directory.listFiles()
            if (files != null) {
                for (file in files) {
                    if (file.delete()) {
                        Log.d(TAG, "Deleted file: ${file.name}")
                    } else {
                        Log.d(TAG, "Failed to delete file: ${file.name}")
                    }
                }
            } else {
                Log.e(TAG, "No files found in the directory.")
            }
        } else {
            Log.e(TAG, "Directory does not exist or is not a directory.")
        }
    }
    //endregion
}