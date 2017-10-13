package com.example.gael.poc_map_tracking_and_gallery.gallery.displaying

import android.util.Log

/**
 * Created on 12.10.17.
 */

class DisplayingPresenter (view : DisplayingContract.View) : DisplayingContract.Presenter {

    lateinit var mView : DisplayingContract.View
    init {
        mView = view
        mView.setPresenter(this)
    }

    override fun isViewNotNull(): Boolean {
        return mView != null
    }

    override fun downloadFile(name: String) {
        if(isViewNotNull()) {
            mView.displayDowload()
        }
    }
}