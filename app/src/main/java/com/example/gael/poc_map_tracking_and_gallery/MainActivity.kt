package com.example.gael.poc_map_tracking_and_gallery

import android.content.pm.PackageManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.util.Log
import android.view.MenuItem
import com.example.gael.poc_map_tracking_and_gallery.map.MapFragment
import com.example.gael.poc_map_tracking_and_gallery.map.MapPresenter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener{

    var bottomNavigation : BottomNavigationView? = null

    lateinit var presenterMap : MapPresenter

    companion object {
        val REQUEST_LOCATION = 896
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bottomNavigation = bottom_nav

        var fragment : MapFragment? = supportFragmentManager.findFragmentById(R.id.container) as? MapFragment
        if(fragment == null) {
            fragment = MapFragment.newIntent()
            supportFragmentManager.beginTransaction().add(R.id.container,fragment,"").commit()
        }

        presenterMap = MapPresenter(fragment)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        return true
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            REQUEST_LOCATION -> if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                if(presenterMap != null){
                    presenterMap.checkPermissionsMap()
                }
            }
        }
    }
}
