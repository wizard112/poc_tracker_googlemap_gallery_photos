package com.example.gael.poc_map_tracking_and_gallery.gallery.displaying

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.gael.poc_map_tracking_and_gallery.Interfaces.IBottomNavigationActivity
import com.example.gael.poc_map_tracking_and_gallery.R
import com.example.gael.poc_map_tracking_and_gallery.adapters.ImageSlidePagerAdapter
import com.example.gael.poc_map_tracking_and_gallery.models.Image
import kotlinx.android.synthetic.main.activity_displaying.*
import java.io.IOException

/**
 * Created on 13.10.17.
 */
 class TestDisplayingFragment : Fragment() {

    var images : ArrayList<Image>? = null
    var firstImage : Int? = null
    var defaultValueInt : Int = -1

    var view_pager : ViewPager? = null
    lateinit var myAdapter : ImageSlidePagerAdapter
    var iComm : IBottomNavigationActivity? = null


    companion object {
        fun newInstance( list : ArrayList<Image>, selectedImage : Int) : TestDisplayingFragment {
            var f : TestDisplayingFragment = TestDisplayingFragment()
            var b : Bundle = Bundle()
            b.putParcelableArrayList(DisplayingActivity.KEY_LIST_IMAGES,list)
            b.putInt(DisplayingActivity.KEY_SELECTED_IMAGE,selectedImage)
            f.arguments = b
            return f
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.fragment_test,container,false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        getArguments(arguments)
    }

    private fun getArguments(arg : Bundle) {
        if(arg.containsKey(DisplayingActivity.KEY_LIST_IMAGES) && arg.containsKey(DisplayingActivity.KEY_SELECTED_IMAGE)){
            firstImage = arg.getInt(DisplayingActivity.KEY_SELECTED_IMAGE,defaultValueInt)
            images = arg.getParcelableArrayList(DisplayingActivity.KEY_LIST_IMAGES)
        }
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view_pager = viewPager
        myAdapter = ImageSlidePagerAdapter(childFragmentManager,activity,images!!,firstImage!!)
        view_pager!!.adapter = myAdapter
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