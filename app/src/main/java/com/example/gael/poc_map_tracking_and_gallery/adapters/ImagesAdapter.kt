package com.example.gael.poc_map_tracking_and_gallery.adapters

import android.content.Context
import android.support.v7.widget.AppCompatCheckBox
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import com.example.gael.poc_map_tracking_and_gallery.Interfaces.IGridGallery
import com.example.gael.poc_map_tracking_and_gallery.R
import com.example.gael.poc_map_tracking_and_gallery.Utils.GalleryUtil
import com.example.gael.poc_map_tracking_and_gallery.models.Image
import kotlinx.android.synthetic.main.layout_cell_recycler_view.*

/**
 * Created on 17.10.17.
 */

class ImagesAdapter (cxt : Context, list : ArrayList<Image>, iCommubication : IGridGallery) : RecyclerView.Adapter<ImagesAdapter.ImageViewHolder>(),
        View.OnLongClickListener {

    private var showCheckBox : Boolean = false
    private var choosedImages : ArrayList<Image> = ArrayList()
    lateinit var context : Context
    lateinit var images : ArrayList<Image>
    lateinit var communication : IGridGallery
    init {
        context = cxt
        images = list
        communication = iCommubication
    }

    override fun getItemCount(): Int {
        return images.size
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ImageViewHolder {
        var itemView : View = LayoutInflater.from(parent!!.context).inflate(R.layout.layout_cell_recycler_view,parent,false)
        return ImageViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ImageViewHolder?, position: Int) {
        //get Image
        val image : Image = images[position]
        holder!!.image.setImageResource(GalleryUtil.choiceGoodImage(images[position]))
        holder!!.image.setOnClickListener(View.OnClickListener {
            var trueNumber = position
            communication.redirectTo(++trueNumber)
        })
        if(showCheckBox){
            holder!!.choice.visibility = View.VISIBLE
        }else{
            holder!!.choice.visibility = View.GONE
        }

        holder!!.image.setOnLongClickListener(this)

        holder!!.choice.setOnClickListener(View.OnClickListener {
            image.checked = holder!!.choice.isChecked

            if(image.checked) {
                addImage(image,position)
            }else{
                deleteImage(image,position)
            }
            Log.i("Test","size is ".plus(choosedImages.size))
        })
    }

    private fun addImage(img : Image, pos : Int) {
        choosedImages.add(img)
        Log.i("Test","saved image ".plus(pos))
        Toast.makeText(context,"saved ".plus(img.toString()),Toast.LENGTH_SHORT).show()
    }

    private fun deleteImage(img : Image, pos : Int) {
        choosedImages.remove(img)
        Log.i("Test","remove image ".plus(pos))
        Toast.makeText(context,"removed ".plus(img.toString()),Toast.LENGTH_SHORT).show()
    }

    override fun onLongClick(v: View?): Boolean {
        showCheckBox = true
        notifyDataSetChanged()
        return false
    }


    open class ImageViewHolder (item : View): RecyclerView.ViewHolder(item) {

        lateinit var image : ImageView
        lateinit var choice : AppCompatCheckBox
        init {
            image = item.findViewById(R.id.image)
            choice = item.findViewById(R.id.choice)
        }
    }
}