package com.example.gael.poc_map_tracking_and_gallery.Utils

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import android.support.v4.app.ActivityCompat
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.example.gael.poc_map_tracking_and_gallery.Interfaces.IPoliline
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

/**
 * Created on 12.10.17.
 */

class Utils {

    companion object {

        /**
         * Check if permission(s) is/are not granted
         * @param : arr represents a permissions array
         * @param : cxt represents the context
         *
         * @return a list of permissions fi are not granted, else empty if is granted
         */
        fun checkPermissions(arr : Array<String>, cxt : Context) : ArrayList<String>{
            var res : ArrayList<String> = ArrayList<String>()
            if(arr != null && arr.size > 0){
                arr.forEach { permission ->
                    if(ActivityCompat.checkSelfPermission(cxt,permission) != PackageManager.PERMISSION_GRANTED) {
                        res.add(permission)
                    }
                }
            }
            return res;
        }

        fun getFRagmentFromManager(cxt : AppCompatActivity, idView: Int) : Fragment {
            return cxt.supportFragmentManager.findFragmentById(idView)
        }

        fun addFragmentFromManager(cxt : AppCompatActivity, idContainer : Int, frg : Fragment) {
            cxt.supportFragmentManager.beginTransaction().add(idContainer,frg,"").commit()
        }

        /**
         * Call Google map Api static to get poliline
         */
        fun executeUrl(theUrl : String, comm : IPoliline) : String {
            var response : String? = null
            Thread(Runnable {
                // Do network action in this function
                try {
                    val url : URL = URL(theUrl)
                    var httpUrlConnection : HttpURLConnection = url.openConnection() as HttpURLConnection
                    if(httpUrlConnection.responseCode != HttpURLConnection.HTTP_OK){
                        Log.i("Test","problem : ".plus(httpUrlConnection.responseCode))
                    } else {
                        response = httpUrlConnection.inputStream.bufferedReader().readText()
                        //Log.i("Test",response)
                        MapUtils.treatPoliline(response.toString(),comm)
                    }
                }catch (ioe : IOException){

                }
            }).start()

            return response.toString()
        }
    }
}