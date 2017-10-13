package com.example.gael.poc_map_tracking_and_gallery

import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.MenuItem
import com.example.gael.poc_map_tracking_and_gallery.gallery.GalleryFragment
import com.example.gael.poc_map_tracking_and_gallery.gallery.GalleryPresenter
import com.example.gael.poc_map_tracking_and_gallery.map.MapFragment
import com.example.gael.poc_map_tracking_and_gallery.map.MapPresenter

/**
 * Created on 13.10.17.
 */

open class BaseActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {

    var bottomNavigation : BottomNavigationView? = null

    var presenterMap : MapPresenter? = null
    var presenterGallery : GalleryPresenter? = null

    var actions : ArrayList<Fragment> = ArrayList()

    /**
     * Create frgament and associated it to presenter : Gallery
     */
    private fun initializePresenterGallery(frg : GalleryFragment) {
        if(presenterGallery == null && frg != null){
            presenterGallery = GalleryPresenter(frg)
        }
    }

    /**
     * Create fragment and associated it to presenter : Map
     */
    private fun initializePresenterMap(frg : MapFragment) {
        if( presenterMap == null && frg != null){
            presenterMap = MapPresenter(frg)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        var fragment : Fragment? = null

        when(item.itemId) {
            R.id.item_gallery -> {
                if (presenterGallery != null) {
                    fragment = presenterGallery!!.getView() as GalleryFragment
                }else{
                    fragment = GalleryFragment.newINstance()
                    initializePresenterGallery(fragment)
                }
            }
            R.id.item_map -> {
                if (presenterMap != null) {
                    fragment = presenterMap!!.getView() as MapFragment
                }else{
                    fragment = MapFragment.newInstance()
                    initializePresenterMap(fragment)
                }
            }
        }
        supportFragmentManager.beginTransaction().replace(R.id.container,fragment,"").commit()
        return true
    }


}