package com.example.gael.poc_map_tracking_and_gallery.share

import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.example.gael.poc_map_tracking_and_gallery.R
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.login.LoginResult
import com.facebook.login.widget.LoginButton
import kotlinx.android.synthetic.main.fragment_share.*
import com.facebook.FacebookException
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.example.gael.poc_map_tracking_and_gallery.Utils.FacebookUtil
import com.facebook.Profile
import java.net.URL
import com.facebook.GraphResponse
import org.json.JSONObject
import com.facebook.GraphRequest
import com.facebook.share.Sharer
import com.facebook.share.model.ShareLinkContent
import com.facebook.share.widget.ShareButton
import com.facebook.share.widget.ShareDialog
import com.squareup.picasso.Picasso


/**
 * Created on 16.10.17.
 */

class ShareFragment : Fragment(), ShareContract.View, View.OnClickListener {

    lateinit var mPresenter : ShareContract.Presenter

    var loginFcb : LoginButton? = null

    lateinit var callbackManager : CallbackManager

    lateinit var shareDialog : ShareDialog

    override fun setPresenter(presenter: ShareContract.Presenter) {
        mPresenter = presenter
    }

    companion object {
        fun newInstance() : ShareFragment{
            return ShareFragment()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var v : View = inflater!!.inflate(R.layout.fragment_share,container,false)

        var profileImageFacebook : ImageView = v.findViewById(R.id.profile_image_facebook)
        var profileNameFacebook : TextView = v.findViewById(R.id.profile_name_facebook)

        shareDialog = ShareDialog(this)

        loginFcb = v.findViewById(R.id.login_button)
        //define permissions
        loginFcb!!.setReadPermissions("email")
        loginFcb!!.setReadPermissions("user_friends")
        loginFcb!!.setReadPermissions("public_profile")
        loginFcb!!.setReadPermissions("user_birthday")
        loginFcb!!.setFragment(this)
        callbackManager = CallbackManager.Factory.create()

        loginFcb!!.registerCallback(callbackManager, object : FacebookCallback<LoginResult> {
            override fun onSuccess(loginResult: LoginResult) {
                // App code
                //Log.i("Test","on success".plus(loginResult.toString()))
                val request = GraphRequest.newMeRequest(
                        loginResult.accessToken
                ) { `object`, response ->

                    // Application code
                    val email = `object`.getString("email")
                    val birthday = `object`.getString("birthday") // 01/31/1980 format
                    val name = `object`.getString("name")

                    //Log.i("Test",email.plus(" ").plus(birthday).plus(" ").plus(name))

                    val profileImgUrl : String = FacebookUtil.getImgUrlProfile(loginResult.accessToken.userId)
                    profileNameFacebook.setText(email)
                    Picasso.with(activity).load(Uri.parse(profileImgUrl)).into(profileImageFacebook)
                    var linear : LinearLayout = v.findViewById(R.id.social_facebook)
                    linear.visibility = View.VISIBLE


                    if(ShareDialog.canShow(ShareLinkContent::class.java)){
                        val linkContent : ShareLinkContent = ShareLinkContent.Builder()
                                .setContentUrl(Uri.parse(FacebookUtil.SHARE_URL))
                                .build()
                        //shareDialog.show(this,linkContent)
                        ShareDialog.show(activity,linkContent)
                    }
                }
                val parameters = Bundle()
                parameters.putString(FacebookUtil.FIELDS,FacebookUtil.FIELDS_PROFILE)
                request.parameters = parameters
                request.executeAsync()

            }

            override fun onCancel() {
                // App code
                Log.i("Test","on cancel")
            }

            override fun onError(exception: FacebookException) {
                // App code
                Log.i("Test","on error ".plus(exception.message))
            }
        })

        shareDialog.registerCallback(callbackManager, object : FacebookCallback<Sharer.Result> {
            override fun onSuccess(result: Sharer.Result?) {

            }

            override fun onCancel() {

            }

            override fun onError(error: FacebookException?) {

            }
        })

        return v
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        callbackManager.onActivityResult(requestCode, resultCode, data)
    }

    override fun onClick(v: View?) {
        when(v!!.id) {
            R.id.share_btn_fb -> {
                Log.i("Test","value is ".plus(ShareDialog.canShow(ShareLinkContent::class.java)))
                /*if(ShareDialog.canShow(ShareLinkContent::class.java)){
                    val linkContent : ShareLinkContent = ShareLinkContent.Builder()
                            .setContentUrl(Uri.parse(FacebookUtil.SHARE_URL))
                            .build()
                    shareDialog.show(linkContent)
                }*/
            }
        }
    }

}