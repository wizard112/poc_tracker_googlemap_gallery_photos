package com.example.gael.poc_map_tracking_and_gallery.models

import android.os.Parcel
import android.os.Parcelable

/**
 * Represents an Image to display in Grid View
 * THis class is parced to write in Intent or Bundle
 * Created on 12.10.17.
 */

class Image() : Parcelable{

    var uriImage : String? = null
    var type : String? = null
    var idIMage : Int? = null
    var checked : Boolean = false

    constructor(parcel: Parcel) : this() {
        uriImage = parcel.readString()
        type = parcel.readString()
        idIMage = parcel.readValue(Int::class.java.classLoader) as? Int
        checked = parcel.readValue(Boolean::class.java.classLoader) as Boolean
    }

    constructor(theUri : String, theType : String, theIdImage : Int) : this() {
        this.uriImage = theUri
        this.type = theType
        this.idIMage = theIdImage
    }

    override fun toString() : String {
        return uriImage.plus(" - ").plus(type).plus(" - ").plus(idIMage)
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(uriImage)
        parcel.writeString(type)
        parcel.writeValue(idIMage)
        parcel.writeValue(checked)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Image> {
        override fun createFromParcel(parcel: Parcel): Image {
            return Image(parcel)
        }

        override fun newArray(size: Int): Array<Image?> {
            return arrayOfNulls(size)
        }
    }
}