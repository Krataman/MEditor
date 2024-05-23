package com.android.pgo

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {
    private val REQUEST_VIDEO_CAPTURE = 1 //static value
    private val REQUEST_VIDEO_PERMISSIONS = 2 //static value
    private val APPLICATION_PERMISSIONS = arrayOf(Manifest.permission.CAMERA) // list of permission that the app requires to function properly

    //region onCreate
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        enableEdgeToEdge()

        initButton()

        val buttonRecordVideo: Button = findViewById(R.id.startFilmingVideoButton)
        buttonRecordVideo.setOnClickListener {
            if (hasPermissions(APPLICATION_PERMISSIONS)) {
                takeVideoIntent()
            } else {
                requestVideoPermissions()
            }
        }

    }
    //endregion
    //region initialize button
    /**
     * Method that initializes the button to take a video
     */
    private fun initButton(){
        val filmVideoButton:Button = findViewById(R.id.startFilmingVideoButton)
        filmVideoButton.setOnClickListener(View.OnClickListener {
            takeVideoIntent()
        })
    }
    //endregion
    //region request permission
    /**
     * Method that requests the video permissions from the user
     */
    private fun requestVideoPermissions() {
        ActivityCompat.requestPermissions(this, APPLICATION_PERMISSIONS, REQUEST_VIDEO_PERMISSIONS)

    }
    //endregion
    //region doesAppHave perms?
    /**
     * Method that checks if the app has
     * the required permissions to work properly
     */
    private fun hasPermissions(permissions: Array<String>): Boolean {
        return permissions.all {
            ContextCompat.checkSelfPermission(this, it) == PackageManager.PERMISSION_GRANTED
        }
    }
    //endregion
    //region onRequestPermissionsResult
    /**
     * Method that handles the result of the permission request.
     * If the user grants the permission, the user will be able to take a video.
     *
     * Else the user will be notified that the permissions are required to continue.
     */
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when (requestCode) { // when = switch
            REQUEST_VIDEO_PERMISSIONS -> {
                if (grantResults.isNotEmpty() && grantResults.all { it == PackageManager.PERMISSION_GRANTED }) {
                    takeVideoIntent()
                } else {
                    Toast.makeText(this, "Permissions to access the camera is required!", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
    //endregion
    //region takeVideo
    /**
     * Method that starts the Intent to take a video.
     */
    @SuppressLint("QueryPermissionsNeeded")
    private fun takeVideoIntent() {
        Intent(MediaStore.ACTION_VIDEO_CAPTURE).also { takeVideoIntent ->
            takeVideoIntent.resolveActivity(packageManager)?.also {
                startActivityForResult(takeVideoIntent, REQUEST_VIDEO_CAPTURE)
            }
        }
    }
    //endregion
    //region onActivityResult
    /**
     * Method that handles the result of the video capture.
     * If the user presses ok, activity TrimVideoActivty will be started.
     */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_VIDEO_CAPTURE && resultCode == RESULT_OK) {

            val videoUri: Uri? = data?.data
            videoUri?.let {
                val intent = Intent(this, TrimVideoActivity::class.java).apply {
                    putExtra("VIDEO_URI", it.toString())
                }
                startActivity(intent)
            }
        }
    }
    //endregion
}