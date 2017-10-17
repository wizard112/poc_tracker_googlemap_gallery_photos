package com.example.gael.poc_map_tracking_and_gallery.share

import com.example.gael.poc_map_tracking_and_gallery.BasePresenter
import com.example.gael.poc_map_tracking_and_gallery.BaseView

/**
 * Created on 16.10.17.
 */

interface ShareContract {

    interface View : BaseView<Presenter> {
        fun sendSMS()
    }

    interface Presenter : BasePresenter {
        fun getView() : View
        fun sendSMS()
    }
}