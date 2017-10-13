package com.example.gael.poc_map_tracking_and_gallery.adapters

import android.content.Context
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.PagerAdapter
import android.util.Log
import android.view.View
import android.view.ViewGroup
import com.example.gael.poc_map_tracking_and_gallery.gallery.displaying.DisplayingFragment
import com.example.gael.poc_map_tracking_and_gallery.gallery.displaying.DisplayingPresenter
import com.example.gael.poc_map_tracking_and_gallery.models.Image

/**
 * Created on 13.10.17.
 */

class ImageSlidePagerAdapter(fm : FragmentManager, cxt : Context,list : ArrayList<Image>, selectedImage : Int) : FragmentPagerAdapter(fm) {

    lateinit var images : ArrayList<Image>
    var firstToDisplay : Int = 0
    lateinit var contexte : Context
    var previews : ArrayList<Fragment> = ArrayList()
    init {
        contexte = cxt
        images = list
        firstToDisplay = selectedImage
        test()
        createFragments()
    }

    private fun test() {
        //images.forEach { image: Image -> Log.i("Test"," : ".plus(image.toString())) }
        val newList = images.sortedWith(compareBy { it -> it.idIMage == firstToDisplay })
        newList.forEach { image: Image ->  Log.i("Test",image.toString())}
    }


    private fun createFragments() {
        images.forEach { image: Image ->
            var preview : DisplayingFragment = DisplayingFragment.newInstance(image)
            val presenter : DisplayingPresenter = DisplayingPresenter(preview)
            previews.add(preview)
        }
    }

    override fun getCount(): Int {
        return images.size
    }

    override fun getItem(position: Int): Fragment {
        return previews[position]
    }

}