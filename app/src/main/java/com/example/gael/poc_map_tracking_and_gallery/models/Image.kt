package com.example.gael.poc_map_tracking_and_gallery.models

/**
 * Created on 12.10.17.
 */

class Image {

    var uriImage : String?
    var type : String?
    var idIMage : Int?

    constructor(theUri : String, theType : String, theIdImage : Int) {
        this.uriImage = theUri
        this.type = theType
        this.idIMage = theIdImage
    }

    override fun toString() : String {
        return uriImage.plus(" - ").plus(type).plus(" - ").plus(idIMage)
    }
}