package com.example.gael.poc_map_tracking_and_gallery.share

/**
 * Created on 16.10.17.
 */

class SharePresenter (view : ShareContract.View): ShareContract.Presenter {

    lateinit var mView : ShareContract.View
    init {
        mView = view
        mView.setPresenter(this)
    }
    override fun isViewNotNull(): Boolean {
        return mView != null
    }

    override fun getView(): ShareContract.View {
        return mView
    }

}