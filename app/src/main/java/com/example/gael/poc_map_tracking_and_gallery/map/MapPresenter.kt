package com.example.gael.poc_map_tracking_and_gallery.map

import android.view.View

/**
 * Created on 11.10.17.
 */

class MapPresenter (v : MapContract.View) : MapContract.Presenter {

    lateinit var mView : MapContract.View
    init {
        mView = v
        mView.setPresenter(this)
    }

    override fun checkPermissionsMap() {
        if(isViewNotNull()) {
            mView.checkPermissionsMap()
        }
    }

    override fun isViewNotNull(): Boolean {
        return mView != null
    }
}