package com.example.gael.poc_map_tracking_and_gallery.gallery.displaying

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
}