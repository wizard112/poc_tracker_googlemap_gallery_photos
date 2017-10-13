package com.example.gael.poc_map_tracking_and_gallery.gallery.displaying

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

/**
 * Created on 12.10.17.
 */

class DisplayingFragment : Fragment(), DisplayingContract.View {

    private var presenter : DisplayingContract.Presenter? = null

    companion object {
        fun newInstance() : DisplayingFragment {
            return DisplayingFragment()
        }
    }

    override fun setPresenter(pres: DisplayingContract.Presenter) {
        presenter = pres
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

}