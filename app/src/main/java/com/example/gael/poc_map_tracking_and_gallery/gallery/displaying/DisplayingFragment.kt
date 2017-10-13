package com.example.gael.poc_map_tracking_and_gallery.gallery.displaying

import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.gael.poc_map_tracking_and_gallery.R
import com.example.gael.poc_map_tracking_and_gallery.models.Image
import kotlinx.android.synthetic.main.fragment_displaying.*

/**
 * Created on 12.10.17.
 */

class DisplayingFragment : Fragment(), DisplayingContract.View {

    private var presenter : DisplayingContract.Presenter? = null
    private var image : Image? = null

    companion object {
        val KEY_IMAGE : String = "key_image"

        fun newInstance(img : Image) : DisplayingFragment {
            var frg : DisplayingFragment =  DisplayingFragment()
            var arguments : Bundle = Bundle()
            arguments.putParcelable(KEY_IMAGE,img)
            frg.arguments = arguments
            return frg
        }
    }

    override fun setPresenter(pres: DisplayingContract.Presenter) {
        presenter = pres
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        getArguments(arguments)
    }

    /**
     * Get the image
     */
    private fun getArguments(arg : Bundle) {
        if(arg.containsKey(KEY_IMAGE)){
            image = arg.getParcelable(KEY_IMAGE)
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.fragment_displaying,container,false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        title_image.text = image!!.type
        preview.setImageURI(Uri.parse(image!!.uriImage))
    }

}