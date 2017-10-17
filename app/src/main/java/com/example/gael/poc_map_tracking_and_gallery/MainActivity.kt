package com.example.gael.poc_map_tracking_and_gallery

import android.content.Intent
import android.content.pm.PackageManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.view.MenuItem
import com.example.gael.poc_map_tracking_and_gallery.Interfaces.IBottomNavigationActivity
import com.example.gael.poc_map_tracking_and_gallery.gallery.GalleryFragment
import com.example.gael.poc_map_tracking_and_gallery.gallery.GalleryPresenter
import com.example.gael.poc_map_tracking_and_gallery.gallery.displaying.TestDisplayingFragment
import com.example.gael.poc_map_tracking_and_gallery.map.MapFragment
import com.example.gael.poc_map_tracking_and_gallery.map.MapPresenter
import com.example.gael.poc_map_tracking_and_gallery.models.Image
import com.example.gael.poc_map_tracking_and_gallery.share.ShareFragment
import com.example.gael.poc_map_tracking_and_gallery.share.SharePresenter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.layout_bottom_view.*
import com.google.android.gms.internal.d
import android.provider.SyncStateContract.Helpers.update
import android.content.pm.PackageInfo
import android.util.Base64
import android.util.Log
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException


class MainActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener, IBottomNavigationActivity {

    var bottomNavigation : BottomNavigationView? = null

    var presenterMap : MapPresenter? = null
    var presenterGallery : GalleryPresenter? = null
    var presenterShare : SharePresenter? = null

    companion object {
        val REQUEST_LOCATION_MAP = 896
        val REQUEST_LOCATION_LOCATION = 897
        val REQUEST_PERMISSION_EXTERNAL_STORAGE = 898
        val REQUEST_PERMISSION_SMS_SEND = 899

        val TAG_FRG_PREVIEW = "tag_frg_preview"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bottomNavigation = bottom_nav

        var fragment : MapFragment? = supportFragmentManager.findFragmentById(R.id.container) as? MapFragment
        if(fragment == null) {
            fragment = MapFragment.newInstance()
            supportFragmentManager.beginTransaction().add(R.id.container,fragment,"").commit()
        }

        initializePresenterMap(fragment)

        (bottomNavigation as BottomNavigationView).setOnNavigationItemSelectedListener(this)
    }

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

    /**
     * Create fragment and associated it to presenter : Share
     */
    private fun initializePresenterSare(frg : ShareFragment) {
        if( presenterShare == null && frg != null){
            presenterShare = SharePresenter(frg)
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
            R.id.item_share -> {
                if(presenterShare != null){
                    fragment = presenterShare!!.getView() as ShareFragment
                }else{
                    fragment = ShareFragment.newInstance()
                    initializePresenterSare(fragment)
                }
            }
        }
        supportFragmentManager.beginTransaction().replace(R.id.container,fragment,"").commit()
        return true
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            REQUEST_LOCATION_MAP -> if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                if(presenterMap != null){
                    presenterMap!!.checkPermissionsMap()
                }
            }
            REQUEST_LOCATION_LOCATION -> if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                if(presenterMap != null){
                    presenterMap!!.checkPermissionsLocation()
                }
            }
            REQUEST_PERMISSION_EXTERNAL_STORAGE -> if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if(presenterGallery != null){
                    presenterGallery!!.getImages()
                }
            }
            REQUEST_PERMISSION_SMS_SEND-> if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if(presenterShare != null){
                    presenterShare!!.sendSMS()
                }
            }
        }
    }

    override fun replaceFragment(list: ArrayList<Image>, selectedImage: Int) {
        var f = supportFragmentManager.findFragmentByTag(TAG_FRG_PREVIEW)
        if( f is TestDisplayingFragment) {
            supportFragmentManager.beginTransaction().replace(R.id.container,f, TAG_FRG_PREVIEW).commit()
        }else{
            f = TestDisplayingFragment.newInstance(list,selectedImage)
            supportFragmentManager.beginTransaction().add(R.id.container,f, TAG_FRG_PREVIEW).commit()
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        var frg : Fragment = supportFragmentManager.findFragmentById(R.id.container)
        if(frg != null && frg is ShareFragment){
            frg.onActivityResult(requestCode, resultCode, data)
        }
    }
}
