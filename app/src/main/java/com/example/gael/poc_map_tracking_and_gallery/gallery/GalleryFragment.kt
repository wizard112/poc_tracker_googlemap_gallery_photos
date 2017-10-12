package com.example.gael.poc_map_tracking_and_gallery.gallery

import android.Manifest
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridView
import com.example.gael.poc_map_tracking_and_gallery.MainActivity
import com.example.gael.poc_map_tracking_and_gallery.R
import com.example.gael.poc_map_tracking_and_gallery.Utils.GalleryUtil
import com.example.gael.poc_map_tracking_and_gallery.Utils.Utils
import com.example.gael.poc_map_tracking_and_gallery.adapters.ImageAdapter
import com.example.gael.poc_map_tracking_and_gallery.models.Image
import kotlinx.android.synthetic.main.fragment_gallery.*

/**
 * Created on 12.10.17.
 */

class GalleryFragment : Fragment(), GalleryContract.View {

    lateinit var presenterGallery : GalleryContract.Presenter

    var images : ArrayList<Image>? = null

    lateinit var gridView : GridView
    lateinit var adapter : ImageAdapter

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

        val list = Utils.checkPermissions(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),activity)
        if(list.size > 0){
            ActivityCompat.requestPermissions(activity, list.toTypedArray(),MainActivity.REQUEST_PERMISSION_EXTERNAL_STORAGE)
        }else{
            getImages()
        }
    }

    override fun getImages() {
        images = GalleryUtil.getAllImages(activity)
        initializeView()
    }

    private fun initializeView(){
        gridView = grid_view
        adapter = ImageAdapter(activity,images!!)
        gridView.adapter = adapter
        gridView.onItemClickListener = adapter
    }
}