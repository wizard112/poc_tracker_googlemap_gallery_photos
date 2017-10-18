package com.example.gael.poc_map_tracking_and_gallery.gallery

import android.Manifest
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.example.gael.poc_map_tracking_and_gallery.Interfaces.IBottomNavigationActivity
import com.example.gael.poc_map_tracking_and_gallery.Interfaces.IGridGallery
import com.example.gael.poc_map_tracking_and_gallery.MainActivity
import com.example.gael.poc_map_tracking_and_gallery.R
import com.example.gael.poc_map_tracking_and_gallery.Utils.GalleryUtil
import com.example.gael.poc_map_tracking_and_gallery.Utils.Utils
import com.example.gael.poc_map_tracking_and_gallery.adapters.ImageAdapter
import com.example.gael.poc_map_tracking_and_gallery.adapters.ImagesAdapter
import com.example.gael.poc_map_tracking_and_gallery.gallery.displaying.DisplayingActivity
import com.example.gael.poc_map_tracking_and_gallery.models.Image
import com.example.gael.poc_map_tracking_and_gallery.observers.ScrollPositionObserver
//import kotlinx.android.synthetic.main.fragment_gallery.*
import kotlinx.android.synthetic.main.fragment_gallery_scroll.*
import kotlinx.android.synthetic.main.layout_grid_view_cell.*
import java.io.IOException
import com.google.android.gms.internal.v
import android.view.ViewParent
import android.view.MotionEvent





/**
 * Created on 12.10.17.
 */

class GalleryFragment : Fragment(), GalleryContract.View, IGridGallery {

    lateinit var presenterGallery : GalleryContract.Presenter

    var images : ArrayList<Image>? = null

    //lateinit var gridView : GridView
    //lateinit var adapter : ImageAdapter
    var iComm : IBottomNavigationActivity? = null

    /*lateinit var image_view : ImageView
    var myLastVisiblePos : Int = 0*/

    lateinit var recyclerView : RecyclerView
    lateinit var adapter : ImagesAdapter

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
        //return inflater!!.inflate(R.layout.fragment_gallery,container,false)
        return inflater!!.inflate(R.layout.fragment_gallery_scroll,container,false)
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

        recyclerView = recycler_view
        recyclerView.setHasFixedSize(true)
        var linearLayoutManager : GridLayoutManager = GridLayoutManager(activity,4)
        recyclerView.layoutManager = linearLayoutManager
        adapter = ImagesAdapter(activity,images!!,this)
        recyclerView.adapter = adapter

        /*gridView = grid_view
        adapter = ImageAdapter(activity,images!!,this)
        gridView.adapter = adapter
        gridView.onItemClickListener = adapter

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            gridView.setNestedScrollingEnabled(true)

        }*/
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