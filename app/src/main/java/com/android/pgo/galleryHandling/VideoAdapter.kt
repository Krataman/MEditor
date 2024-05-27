package com.android.pgo.galleryHandling

import android.content.Context
import android.graphics.Bitmap
import android.media.ThumbnailUtils
import android.provider.MediaStore
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.android.pgo.R

class VideoAdapter(
    private val context: Context, // prijima kontext tridy GalleryAcitivity pro pozdejsi ziskani paremtru display uzivatele
    private val videoPaths: List<String>,
    private val onVideoClick: (String) -> Unit // lambda funkce, volana pri kliku na video | UNIT -> nic nevraci / void v java || String je cesta k videu
):RecyclerView.Adapter<VideoAdapter.ViewHolder>() {

    private val thumbnailWidth: Int
    init {
        val displayMetrics: DisplayMetrics = context.resources.displayMetrics
        val screenWidth = displayMetrics.widthPixels
        thumbnailWidth = screenWidth / 4
    }
    //region onCreateViewHolder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.video_item, parent, false)
        return ViewHolder(view)
    }
    //endregion
    //region onBindViewHolder
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        //region dynamicke nastaveni sirky a vysky nahledu
        val layoutParams = holder.videoThumbnail.layoutParams
        layoutParams.width = thumbnailWidth
        layoutParams.height = thumbnailWidth
        holder.videoThumbnail.layoutParams = layoutParams
        //endregion

        val videoPath = videoPaths[position]
        holder.itemView.setOnClickListener { onVideoClick(videoPath) }
        val thumbnail: Bitmap? = ThumbnailUtils.createVideoThumbnail(videoPath, MediaStore.Images.Thumbnails.MINI_KIND)
        holder.videoThumbnail.setImageBitmap(thumbnail)
    }
    //endregion
    override fun getItemCount(): Int {
        return videoPaths.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val videoThumbnail: ImageView = view.findViewById(R.id.videoThumbnail)
    }
}