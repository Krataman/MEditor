package com.android.pgo

import java.io.File

class DataHolder() { //SINGLETON CLASS
    companion object {
        val mediaPaths: ArrayList<String> = ArrayList()

        var load = true // boolean ktery ve tride Gallery zajistuje,
                            // aby se videa do galerie nacetly POUZE 1x, tim ze je prommena v Singletonu tak zustava po celou dobu kdyz program bezi

        //region onLoadAddVideoPaths
        fun onLoadAddMediaPaths(){
            val videoPath = "/storage/emulated/0/Android/data/com.android.pgo/files/TrimmedVideo/"
            val videoDir = File(videoPath)

            if (videoDir.exists() && videoDir.isDirectory) {
                val files = videoDir.listFiles()

                if(files != null){
                    for(file in files){
                        mediaPaths.add(file.absolutePath)
                    }
                }
            }

            val imagePath = "/storage/emulated/0/Android/data/com.android.pgo/files/Pictures/"
            val imgDir = File(imagePath)

            if (imgDir.exists() && imgDir.isDirectory) {
                val files = imgDir.listFiles()

                if(files != null){
                    for(file in files){
                        mediaPaths.add(file.absolutePath)
                    }
                }
            }
        }
        //endregion
    }
}