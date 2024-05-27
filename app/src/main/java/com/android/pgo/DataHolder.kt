package com.android.pgo

import java.io.File

class DataHolder() { //SINGLETON CLASS
    companion object {
        var videoPaths: ArrayList<String> = arrayListOf()

        var loadVids = true // boolean ktery ve tride Gallery zajistuje,
                            // aby se videa do galerie nacetly POUZE 1x, tim ze je prommena v Singletonu tak zustava po celou dobu kdyz program bezi

        //region onLoadAddVideoPaths
        fun onLoadAddVideoPaths(){
            val directoryPath = "/storage/emulated/0/Android/data/com.android.pgo/files/TrimmedVideo/"
            val directory = File(directoryPath)

            if (directory.exists() && directory.isDirectory) {
                val files = directory.listFiles()

                if(files != null){
                    for(file in files){
                        videoPaths.add(file.absolutePath)
                    }
                }
            }
        }
        //endregion
    }
}