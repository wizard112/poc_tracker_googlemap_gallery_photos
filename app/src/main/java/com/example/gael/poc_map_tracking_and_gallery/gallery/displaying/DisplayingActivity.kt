package com.example.gael.poc_map_tracking_and_gallery.gallery.displaying

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.view.ViewPager

import com.example.gael.poc_map_tracking_and_gallery.R
import com.example.gael.poc_map_tracking_and_gallery.adapters.ImageSlidePagerAdapter
import com.example.gael.poc_map_tracking_and_gallery.models.Image
import kotlinx.android.synthetic.main.activity_displaying.*

class DisplayingActivity : AppCompatActivity() {

    var images : ArrayList<Image>? = null
    var firstImage : Int? = null
    var defaultValueInt : Int = -1

    var view_pager : ViewPager? = null
    lateinit var myAdapter : ImageSlidePagerAdapter


    companion object {
        val KEY_LIST_IMAGES = "Key_images"
        //selected image to display first
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

        view_pager = viewPager
        myAdapter = ImageSlidePagerAdapter(supportFragmentManager,this,images!!,firstImage!!)
        view_pager!!.adapter = myAdapter
    }

    private fun getIntent(intent : Intent) {
        if(intent != null && intent.hasExtra(KEY_LIST_IMAGES) && intent.hasExtra(KEY_SELECTED_IMAGE)){
            firstImage = intent.getIntExtra(KEY_SELECTED_IMAGE,defaultValueInt)
            images = intent.getParcelableArrayListExtra(KEY_LIST_IMAGES)
        }
    }
}
