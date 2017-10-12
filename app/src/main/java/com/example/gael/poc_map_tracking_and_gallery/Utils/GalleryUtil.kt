package com.example.gael.poc_map_tracking_and_gallery.Utils

import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
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
                //images.add(Image(name,bucketName,number++))
            }
            return images
        }

    }
}