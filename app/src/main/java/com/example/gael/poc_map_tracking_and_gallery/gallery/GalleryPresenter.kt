package com.example.gael.poc_map_tracking_and_gallery.gallery

/**
 * Created on 12.10.17.
 */
class GalleryPresenter (view : GalleryContract.View): GalleryContract.Presenter {

    lateinit var mView : GalleryContract.View
    init {
        mView = view
        mView.setPresenter(this)
    }
    override fun isViewNotNull(): Boolean {
        return (mView != null)
    }

    override fun getView(): GalleryContract.View {
        return mView
    }

    override fun getImages() {
        if(isViewNotNull()){
            mView.getImages()
        }
    }

    override fun goToDisplayingView(pos : Int) {
        if(isViewNotNull()) {
            mView.goToDisplayingView(pos)
        }
    }
}