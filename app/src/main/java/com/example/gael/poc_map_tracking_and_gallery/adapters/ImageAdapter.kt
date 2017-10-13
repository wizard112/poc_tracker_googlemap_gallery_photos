package com.example.gael.poc_map_tracking_and_gallery.adapters

import android.content.Context
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.BaseAdapter
import com.example.gael.poc_map_tracking_and_gallery.Interfaces.IGridGallery
import com.example.gael.poc_map_tracking_and_gallery.R
import com.example.gael.poc_map_tracking_and_gallery.models.Image
import kotlinx.android.synthetic.main.layout_grid_view_cell.view.*

/**
 * This adapter is used for grid view in Gallery view
 * Created on 12.10.17.
 */

class ImageAdapter (cxt : Context, list : ArrayList<Image>, iCommubication : IGridGallery): BaseAdapter(),  AdapterView.OnItemClickListener{

    lateinit var context : Context
    lateinit var images : ArrayList<Image>
    lateinit var communication : IGridGallery
    init {
        context = cxt
        images = list
        communication = iCommubication
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var myView : View = LayoutInflater.from(context).inflate(R.layout.layout_grid_view_cell,parent,false)
        myView.image.setImageURI(Uri.parse(images[position].uriImage))
        return myView
    }

    override fun getItem(position: Int): Any {
        return images[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return images.size
    }

    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        var trueNumber = position
        communication.redirectTo(++trueNumber)
    }
}