package com.example.gael.poc_map_tracking_and_gallery.gallery

import android.Manifest
import android.content.Context
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridView
import com.example.gael.poc_map_tracking_and_gallery.Interfaces.IBottomNavigationActivity
import com.example.gael.poc_map_tracking_and_gallery.Interfaces.IGridGallery
import com.example.gael.poc_map_tracking_and_gallery.MainActivity
import com.example.gael.poc_map_tracking_and_gallery.R
import com.example.gael.poc_map_tracking_and_gallery.Utils.GalleryUtil
import com.example.gael.poc_map_tracking_and_gallery.Utils.Utils
import com.example.gael.poc_map_tracking_and_gallery.adapters.ImageAdapter
import com.example.gael.poc_map_tracking_and_gallery.gallery.displaying.DisplayingActivity
import com.example.gael.poc_map_tracking_and_gallery.models.Image
import kotlinx.android.synthetic.main.fragment_gallery.*
import java.io.IOException

/**
 * Created on 12.10.17.
 */

class GalleryFragment : Fragment(), GalleryContract.View, IGridGallery {

    lateinit var presenterGallery : GalleryContract.Presenter

    var images : ArrayList<Image>? = null

    lateinit var gridView : GridView
    lateinit var adapter : ImageAdapter
    var iComm : IBottomNavigationActivity? = null

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

    /**
     * Images list from Gallery utils
     */
    override fun getImages() {
        //images = GalleryUtil.getAllImages(activity)
        images = GalleryUtil.createLIstImageDrawable()
        initializeView()
    }

    /**
     * Initialize the grid view with adapter and click event
     */
    private fun initializeView(){
        gridView = grid_view
        adapter = ImageAdapter(activity,images!!,this)
        gridView.adapter = adapter
        gridView.onItemClickListener = adapter
    }

    /**
     * redirect to displaying activity
     */
    override fun goToDisplayingView(position: Int) {
        //activity.startActivity(DisplayingActivity.newIntent(activity, images!!,position))
        iComm!!.replaceFragment(images!!,position)
    }

    /**
     * Preent knows the function to execute
     */
    override fun redirectTo(position: Int) {
        presenterGallery.goToDisplayingView(position)
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        try{
            iComm = context as IBottomNavigationActivity
        }catch (ioe: IOException) {
            ioe.printStackTrace()
            Log.i("Test",ioe.message)
        }
    }
}