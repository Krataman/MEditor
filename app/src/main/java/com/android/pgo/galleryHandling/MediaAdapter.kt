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
import com.bumptech.glide.Glide

class MediaAdapter(
    private val context: Context, // prijima kontext tridy GalleryAcitivity pro pozdejsi ziskani paremtru display uzivatele
    private val mediaPaths: List<String>,
    private val onItemClick: (String) -> Unit // lambda funkce, volana pri kliku na video | UNIT -> nic nevraci / void v java || String je cesta k videu
):RecyclerView.Adapter<MediaAdapter.ViewHolder>() {

    private val thumbnailWidth: Int
    init {
        val displayMetrics: DisplayMetrics = context.resources.displayMetrics
        val screenWidth = displayMetrics.widthPixels
        thumbnailWidth = screenWidth / 4
    }
    //region onCreateViewHolder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.gallery_thumbnail, parent, false)
        return ViewHolder(view)
    }
    //endregion
    //region onBindViewHolder
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val mediaPath = mediaPaths[position]
        val layoutParams = holder.thumbnail.layoutParams
        layoutParams.width = thumbnailWidth
        layoutParams.height = thumbnailWidth
        holder.thumbnail.layoutParams = layoutParams

        // Rozhodnutí, zda se jedná o video nebo obrázek
        if (mediaPath.endsWith(".mp4")) {
            val thumbnail: Bitmap? = ThumbnailUtils.createVideoThumbnail(mediaPath, MediaStore.Images.Thumbnails.MINI_KIND)
            holder.thumbnail.setImageBitmap(thumbnail)
        } else {
            // Pokud je cesta k obrázku, načtěte obrázek pomocí knihovny pro manipulaci s obrázky
            Glide.with(context).load(mediaPath).into(holder.thumbnail)
        }

        holder.itemView.setOnClickListener { onItemClick(mediaPath) }
    }
    //endregion
    override fun getItemCount(): Int {
        return mediaPaths.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val thumbnail: ImageView = view.findViewById(R.id.thumbnail)
    }
}