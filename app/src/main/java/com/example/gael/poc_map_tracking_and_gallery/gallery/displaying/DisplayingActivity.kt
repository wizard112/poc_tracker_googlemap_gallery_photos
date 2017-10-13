package com.example.gael.poc_map_tracking_and_gallery.gallery.displaying

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

import com.example.gael.poc_map_tracking_and_gallery.R
import com.example.gael.poc_map_tracking_and_gallery.Utils.Utils
import com.example.gael.poc_map_tracking_and_gallery.models.Image

class DisplayingActivity : AppCompatActivity() {

    lateinit var presenter : DisplayingPresenter

    var images : ArrayList<Image>? = null
    var firstImage : Int? = null
    var defaultValueInt : Int = -1


    companion object {
        val KEY_LIST_IMAGES = "Key_images"
        //selected image
        val KEY_SELECTED_IMAGE = "Key_selected_image"

        fun newIntent(cxt : Context, list : ArrayList<Image>, selectedImage : Int) : Intent {
            var intent : Intent = Intent(cxt,DisplayingActivity::class.java)
            intent.putExtra(KEY_LIST_IMAGES,list)
            intent.putExtra(KEY_SELECTED_IMAGE,selectedImage)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_displaying)

        getIntent(intent)

        //var frg : DisplayingFragment? = Utils.getFRagmentFromManager(this,R.id.container) as? DisplayingFragment
        var frg : DisplayingFragment? = supportFragmentManager.findFragmentById(R.id.container) as? DisplayingFragment
        if(frg == null){
            frg = DisplayingFragment.newInstance()
        }

        Utils.addFragmentFromManager(this,R.id.container,frg)

        presenter = DisplayingPresenter(frg)
    }

    private fun getIntent(intent : Intent) {
        if(intent != null && intent.hasExtra(KEY_LIST_IMAGES) && intent.hasExtra(KEY_SELECTED_IMAGE)){
            firstImage = intent.getIntExtra(KEY_SELECTED_IMAGE,defaultValueInt)
            images = intent.getParcelableArrayListExtra(KEY_LIST_IMAGES)
        }
    }
}
