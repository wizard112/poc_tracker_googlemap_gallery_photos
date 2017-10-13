package com.example.gael.poc_map_tracking_and_gallery.Utils

import android.content.Context
import android.database.Cursor
import android.graphics.drawable.Drawable
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import com.example.gael.poc_map_tracking_and_gallery.R
import com.example.gael.poc_map_tracking_and_gallery.models.Image
import kotlin.collections.ArrayList

/**
 * Created on 12.10.17.
 */

class GalleryUtil {

    companion object {

        /**
         * This function allows to get images from Gallery
         * @return list of images
         */
        fun getAllImages(cxt :Context) : ArrayList<Image>{
            var uri : Uri
            var cursor : Cursor
            var column_index_data  : Int
            var column_index_folder_name : Int

            var images : ArrayList<Image>

            uri = android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI

            val projection = arrayOf(MediaStore.MediaColumns.DATA,
                    MediaStore.Images.Media.BUCKET_DISPLAY_NAME)

            cursor = cxt.getContentResolver().query(uri, projection, null, null, null)
            column_index_data = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA)
            column_index_folder_name = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_DISPLAY_NAME)

            images = arrayListOf()
            var number : Int = 1
            while (cursor.moveToNext()){
                val name : String = cursor.getString(column_index_data)
                val bucketName = cursor.getString(column_index_folder_name)
                if(number < 6) {
                    images.add(Image(name,bucketName,number++))
                }
            }
            return images
        }

        /**
         * Create a list of drawable
         */
        fun createLIstImageDrawable() : ArrayList<Image> {
            var l : ArrayList<Image> = ArrayList()
            var i : Int = 1
            while(i < 201){
                var image : Image = Image()
                when{
                    i%3 == 0 -> {
                        image.type = "camera"
                        image.uriImage = "a"
                        image.idIMage = i
                    }
                    i%3 == 1 -> {
                        image.type = "social"
                        image.uriImage = "m"
                        image.idIMage = i
                    }
                    i%3 == 2 -> {
                        image.type = "holiday"
                        image.uriImage = "s"
                        image.idIMage = i
                    }
                }
                l.add(image)
                i++
            }
            return l
        }

        /**
         * Give the good image
         */
        fun choiceGoodImage(image : Image) : Int {
            var d : Int? = null
            when(image.uriImage){
                "a" -> {
                    d = R.drawable.allrigth
                }
                "m" -> {
                    d = R.drawable.mon
                }
                "s" -> {
                    d = R.drawable.see
                }
            }
            return d!!
        }

    }
}