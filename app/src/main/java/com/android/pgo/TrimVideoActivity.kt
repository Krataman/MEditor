package com.android.pgo

import android.app.Activity
import android.content.ContentValues.TAG
import android.media.MediaDrm
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.VideoView
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.gowtham.library.utils.LogMessage
import com.gowtham.library.utils.TrimVideo


class TrimVideoActivity : AppCompatActivity() {

    private lateinit var videoView: VideoView
    private lateinit var videoUri: Uri


    val startForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            result: ActivityResult ->
        if (result.resultCode == Activity.RESULT_OK &&
            result.data != null) {
            val uri:Uri = Uri.parse(TrimVideo.getTrimmedVideoPath(result.data))
            Log.d(TAG, "Trimmed path:: " + uri)
        }else
            LogMessage.v("videoTrimResultLauncher data is null");
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_trim_video)
        enableEdgeToEdge()

        //videoView = findViewById(R.id.videoView)
        val uriString = intent.getStringExtra("VIDEO_URI")

        if (uriString != null) {
            videoUri = Uri.parse(uriString) // parse do Uri
        }
        openTrimActivity()
    }

    private fun openTrimActivity(){
        TrimVideo.activity(videoUri.toString())
            .setHideSeekBar(true)
            .start(this,startForResult);
    }


}
