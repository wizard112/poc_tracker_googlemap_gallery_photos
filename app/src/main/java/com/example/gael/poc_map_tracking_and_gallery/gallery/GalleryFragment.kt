package com.example.gael.poc_map_tracking_and_gallery.gallery

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.gael.poc_map_tracking_and_gallery.R
import kotlinx.android.synthetic.main.fragment_gallery.*

/**
 * Created on 12.10.17.
 */

class GalleryFragment : Fragment(), GalleryContract.View {

    lateinit var presenterGallery : GalleryContract.Presenter

    companion object {
        fun newINstance() : GalleryFragment{
            return GalleryFragment()
        }
    }

    override fun setPresenter(presenter: GalleryContract.Presenter) {
        presenterGallery = presenter
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.fragment_gallery,container,false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}