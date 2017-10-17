package com.example.gael.poc_map_tracking_and_gallery.share

import android.util.Log
import com.google.android.gms.auth.api.signin.GoogleSignInResult

/**
 * Created on 16.10.17.
 */

class SharePresenter (view : ShareContract.View): ShareContract.Presenter {

    lateinit var mView : ShareContract.View
    init {
        mView = view
        mView.setPresenter(this)
    }
    override fun isViewNotNull(): Boolean {
        return mView != null
    }

    override fun getView(): ShareContract.View {
        return mView
    }

    override fun sendSMS() {
        if(isViewNotNull()) {
            mView.sendSMS()
        }
    }

    override fun sendResultSignin(result: GoogleSignInResult) {
        if(isViewNotNull()) {
            mView.resultSignin(result)
            Log.i("Test","in presenter for view ")
        }
    }

}