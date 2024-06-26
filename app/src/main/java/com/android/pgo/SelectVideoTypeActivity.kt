package com.android.pgo

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.ContentValues
import android.content.ContentValues.TAG
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.gowtham.library.utils.LogMessage
import com.gowtham.library.utils.TrimVideo

class SelectVideoTypeActivity : AppCompatActivity() {
    companion object {
        private const val REQUEST_VIDEO_CAPTURE = 1 //static value
        private const val REQUEST_VIDEO_PERMISSIONS = 2 //static value
        private const val REQUEST_VIDEO_PICK = 1
        private val APPLICATION_PERMISSIONS = arrayOf(Manifest.permission.CAMERA) // list of permission that the app requires to function properly
        private lateinit var videoUri: Uri
        private val trimmedVideoPaths = mutableListOf<String>()

    }

    //region value startForResult
    val startForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            result: ActivityResult ->
        if (result.resultCode == Activity.RESULT_OK && result.data != null) {

            val uri: Uri = Uri.parse(TrimVideo.getTrimmedVideoPath(result.data))
            Log.d(ContentValues.TAG, "Trimmed video path:: " + uri)
            saveTrimmedVideoPath(uri.toString()) // ulozi lokaci trimnuteho videa to listu Stringu

            Log.d(TAG, "$uri")

        }else
            LogMessage.v("Video trimmer data is null!");
    }
    //endregion
    //region onCreate
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_select_video)
        initButton()
    }
    //endregion
    //region initialize button
    /**
     * Method that initializes the buttons to take a video
     */
    private fun initButton(){
        //region filmVideo
        val filmVideoButton: Button = findViewById(R.id.takeVideoButton)
        filmVideoButton.setOnClickListener(View.OnClickListener {
            if (hasPermissions(APPLICATION_PERMISSIONS)) {
                takeVideoIntent()
            } else {
                requestCameraPermissions()
            }
        })
        //endregion
        //region pickVideo
        val pickVideoButton: Button = findViewById(R.id.videoFromGalleryButton)
        pickVideoButton.setOnClickListener(View.OnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "video/*" // typ souboru ktery uzivatel muze vybrat
            startActivityForResult(intent, REQUEST_VIDEO_PICK)
        })
        //endregion
    }
    //endregion
    //region request permission
    /**
     * Method that requests the video permissions from the user
     */
    private fun requestCameraPermissions() {
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
            videoUri = data?.data!!
            startTrim()
        }
    }
    //endregion
    //region openTrimAcitivity
    private fun startTrim(){
        TrimVideo.activity(videoUri.toString()).
        setHideSeekBar(false).
        setAccurateCut(false).
        start(this,startForResult)
    }
    //endregion
    //region saveTrimmedVideoPath
    /**
     * Stores all paths to trimmed videos in a list.
     */
    private fun saveTrimmedVideoPath(videoPath: String) {
        trimmedVideoPaths.add(videoPath)
    }
    //endregion
    //region onBackPressed
    /**
     * Checks if the back button on users phone has been pressen,
     * if so the user will be transfered to MainAcitivty while simultaneously passing the list of trimmed video paths.
     */
    override fun onBackPressed() {
        super.onBackPressed()
        DataHolder.mediaPaths.addAll(trimmedVideoPaths)
        trimmedVideoPaths.clear() //smaze cely list aby se videa v galerii neduplikovala ,
        // jelikoz jsem predelal aby se videa pridavali pomoci .addAll() misto toho aby list nahrazovali
        finish()

    }
    //endregion
}