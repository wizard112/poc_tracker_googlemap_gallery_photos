package com.example.gael.poc_map_tracking_and_gallery.observers

import android.content.Context
import android.util.Log
import android.view.ViewTreeObserver
import android.widget.ImageView
import android.widget.ScrollView
import com.example.gael.poc_map_tracking_and_gallery.R

/**
 * Interface will be invoked when the view tree has been scrolled
 * Created on 17.10.17.
 */

class ScrollPositionObserver (cxt : Context, scrollView : ScrollView, img : ImageView): ViewTreeObserver.OnScrollChangedListener {

    lateinit var context : Context
    lateinit var mScrollView : ScrollView
    lateinit var picture : ImageView
    var mImageViewHeight : Int
    init {
        context = cxt
        mScrollView = scrollView
        mImageViewHeight = context.resources.getDimensionPixelSize(R.dimen.image_view_heigt)
        picture = img
    }

    override fun onScrollChanged() {
        Log.i("Test","method is called here ")
        val scrollY = Math.min(Math.max(mScrollView.scrollY,0),mImageViewHeight)

        //changing position ImageView or other  views
        picture.translationY = (scrollY / 2).toFloat()

        //alpha you could set ot ActionBar background
        val alpha : Float = (scrollY / mImageViewHeight).toFloat()

        Log.i("Test","values are : ".plus(mImageViewHeight).plus(" - ").plus(scrollY).plus(" - ").plus(alpha).plus((scrollY / 2).toFloat()).plus(" - ").plus(scrollY/2))
    }

}