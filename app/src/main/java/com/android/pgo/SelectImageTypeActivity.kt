package com.android.pgo

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.icu.text.SimpleDateFormat

import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast

import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import com.yalantis.ucrop.UCrop
import java.io.File
import java.io.FileNotFoundException
import java.io.IOException
import java.io.InputStream
import java.util.Date
import java.util.Locale

class SelectImageTypeActivity : AppCompatActivity() {

    companion object {
        private val PICK_IMAGE_REQUEST = 1
        private val TAKE_PHOTO_REQUEST = 2
        private val APPLICATION_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
        private lateinit var currentPhotoPath: String
        private val croppedImagesPaths = mutableListOf<String>()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_select_image)
        iniButtons()
    }
    //region init
    private fun iniButtons(){
        val btnSelectFromGallery: Button = findViewById(R.id.imageFromGalleryButton)
        val btnTakePhoto: Button = findViewById(R.id.takePhotoButton)

        btnSelectFromGallery.setOnClickListener {
            pickImageFromGallery()
        }

        btnTakePhoto.setOnClickListener {
            if(hasPermissions(APPLICATION_PERMISSIONS)){
                takePhoto()
            }else{
                requestCameraPermissions()
            }

        }
    }
    //endregion
    //region picImageFromGallery
    private fun pickImageFromGallery() {
        val intent = Intent(Intent.ACTION_GET_CONTENT).apply {
            type = "image/*"
            addCategory(Intent.CATEGORY_OPENABLE)
        }
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST)
    }
    //endregion
    //region takePhoto
    private fun takePhoto() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        val photoFile: File? = createImageFile()
        photoFile?.also {
            val photoURI: Uri = FileProvider.getUriForFile(
                this,
                "com.android.pgo.fileprovider",
                it
            )
            intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
            startActivityForResult(intent, TAKE_PHOTO_REQUEST)
        }
    }
    //endregion
    //region createImageFile
    @Throws(IOException::class)
    private fun createImageFile(): File {
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storageDir: File = getExternalFilesDir(Environment.DIRECTORY_PICTURES)!!
        return File.createTempFile(
            "JPEG_${timeStamp}_",
            ".jpg",
            storageDir
        ).apply {
            currentPhotoPath = absolutePath
        }
    }
    //endregion
    //region permissions
    private fun hasPermissions(permissions: Array<String>): Boolean {
        return permissions.all {
            ContextCompat.checkSelfPermission(this, it) == PackageManager.PERMISSION_GRANTED
        }
    }

    private fun requestCameraPermissions() {
        ActivityCompat.requestPermissions(this, APPLICATION_PERMISSIONS, TAKE_PHOTO_REQUEST
        )

    }
    //endregion
    //region cropActivity
    private fun openCropActivity(sourceUri: Uri, destinationUri: Uri) {
        UCrop.of(sourceUri, destinationUri)
            .withAspectRatio(1f, 1f)
            .withMaxResultSize(450, 450)
            .start(this)
    }
    //endregion
    //region onresult
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                PICK_IMAGE_REQUEST -> {
                    val sourceUri = data?.data ?: return
                    val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
                    val storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
                    val destinationUri = Uri.fromFile(File(storageDir, "cropped_image_$timeStamp.jpg"))
                    openCropActivity(sourceUri, destinationUri)
                    saveImagePath(destinationUri.toString())
                }
                TAKE_PHOTO_REQUEST -> {
                    val sourceUri = Uri.fromFile(File(currentPhotoPath))
                    val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
                    val storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
                    val destinationUri = Uri.fromFile(File(storageDir, "cropped_image_$timeStamp.jpg"))
                    openCropActivity(sourceUri, destinationUri)
                    saveImagePath(destinationUri.toString())

                }
                UCrop.REQUEST_CROP -> {
                    val uri = Uri.parse(UCrop.getOutput(data!!).toString())
                    Log.d("!!! C !!!", "PATH: $uri")
                }
            }
        } else if (resultCode == UCrop.RESULT_ERROR) {
            val cropError = UCrop.getError(data!!)
            // Zpracování chyby
            Log.e("UCrop", "Crop error: $cropError")
        }
    }
    //endregion
    //region permission Reuslts
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when (requestCode) { // when = switch
            TAKE_PHOTO_REQUEST -> {
                if (grantResults.isNotEmpty() && grantResults.all { it == PackageManager.PERMISSION_GRANTED }) {
                    takePhoto()
                } else {
                    Toast.makeText(this, "Permissions to access the camera is required!", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
    //endregion
    //region saveImagePaths
    private fun saveImagePath(imagePath: String) {
        croppedImagesPaths.add(imagePath)
    }
    //endregion
    //region onBackPressed
    override fun onBackPressed() {
        super.onBackPressed()
        DataHolder.mediaPaths.addAll(croppedImagesPaths)
        croppedImagesPaths.clear()
        finish()
    }
    //endregion

}