package com.example.gael.poc_map_tracking_and_gallery.gallery.displaying

import com.example.gael.poc_map_tracking_and_gallery.BasePresenter
import com.example.gael.poc_map_tracking_and_gallery.BaseView

/**
 * Created on 12.10.17.
 */

interface DisplayingContract {

    interface View : BaseView<Presenter> {}
    interface Presenter: BasePresenter{}
}