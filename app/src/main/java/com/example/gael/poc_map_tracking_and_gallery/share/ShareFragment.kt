package com.example.gael.poc_map_tracking_and_gallery.share

import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import android.support.v4.app.ActivityCompat
import android.support.v7.app.AppCompatActivity
import android.telephony.SmsManager
import android.widget.*
import com.example.gael.poc_map_tracking_and_gallery.MainActivity
import com.example.gael.poc_map_tracking_and_gallery.Manifest
import com.example.gael.poc_map_tracking_and_gallery.Utils.FacebookUtil
import com.example.gael.poc_map_tracking_and_gallery.Utils.Utils
import com.example.gael.poc_map_tracking_and_gallery.map.MapFragment
import com.facebook.Profile
import java.net.URL
import com.facebook.GraphResponse
import org.json.JSONObject
import com.facebook.GraphRequest
import com.facebook.share.Sharer
import com.facebook.share.model.ShareLinkContent
import com.facebook.share.widget.ShareButton
import com.facebook.share.widget.ShareDialog
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.auth.api.signin.GoogleSignInResult
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.GoogleApiClient
import com.google.firebase.database.Transaction.success
import com.squareup.picasso.Picasso
import com.twitter.sdk.android.core.*
import com.twitter.sdk.android.core.identity.TwitterLoginButton
import com.twitter.sdk.android.tweetcomposer.TweetComposer


/**
 * Created on 16.10.17.
 */

class ShareFragment : Fragment(), ShareContract.View, View.OnClickListener,
 GoogleApiClient.OnConnectionFailedListener{

    lateinit var mPresenter : ShareContract.Presenter

    var loginFcb : LoginButton? = null

    lateinit var callbackManager : CallbackManager

    lateinit var shareDialog : ShareDialog

    lateinit var loginButtonTwitter : TwitterLoginButton

    lateinit var btnSendSMS : Button

    lateinit var mGoogleApiClient : GoogleApiClient

    var signINGoogle : SignInButton? = null

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

        var gso : GoogleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestEmail()
        .build()

        mGoogleApiClient = GoogleApiClient.Builder(activity)
        .enableAutoManage(activity, this)
        .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
        .build()
    }

    override fun onConnectionFailed(p0: ConnectionResult) {
        Log.i("Test","failed : ".plus(p0.errorMessage))
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var v : View = inflater!!.inflate(R.layout.fragment_share,container,false)

        manageTwitter(v)
        manageFacebook(v)

        return v
    }

    private fun manageFacebook(v : View){
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
                Log.i("Test","in oncancel")
            }

            override fun onError(error: FacebookException?) {
                Log.i("Test","error ".plus(error!!.message))
            }
        })
    }

    private fun manageTwitter(v : View) {
        loginButtonTwitter = v.findViewById(R.id.login_button_twitter)
        loginButtonTwitter.callback = object : Callback<TwitterSession> (){
            override fun success(result: Result<TwitterSession>?) {
                Log.i("Test",result.toString())
                var session : TwitterSession = TwitterCore.getInstance().sessionManager.activeSession
                var authToken : TwitterAuthToken = session.authToken
                var secret : String = authToken.secret

                /*Log.i("Test","in onsuccess")
                var builder : TweetComposer.Builder = TweetComposer.Builder(activity)
                        .text("Test my post from app !!!")
                builder.show()*/
            }

            override fun failure(exception: TwitterException?) {
                Log.i("Test",exception!!.message)
            }
        }
    }


    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        manageBtnSendSMS()
        manageGoogle()
    }

    private fun manageBtnSendSMS() {
        btnSendSMS = btn_send_message
        btnSendSMS.setOnClickListener(this)
    }

    private fun manageGoogle() {
        signINGoogle = sign_in_button
        signINGoogle!!.setOnClickListener(this)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        callbackManager.onActivityResult(requestCode, resultCode, data)

        if(requestCode == MainActivity.CODE_SIGNIN_GOOGLE){
            val result : GoogleSignInResult = Auth.GoogleSignInApi.getSignInResultFromIntent(data)
            mPresenter.sendResultSignin(result)
        }
    }

    override fun onClick(v: View?) {
        when(v!!.id) {
            R.id.share_btn_fb -> {
                Log.i("Test","value is ".plus(ShareDialog.canShow(ShareLinkContent::class.java)))
            }
            R.id.btn_send_message -> {
                var arr : ArrayList<String> = Utils.checkPermissions(arrayOf(android.Manifest.permission.SEND_SMS),activity)
                if(arr != null && arr.size > 0){
                    ActivityCompat.requestPermissions(activity,arr.toTypedArray(),MainActivity.REQUEST_PERMISSION_SMS_SEND)
                }else{
                    sendSMS()
                }
            }
            R.id.sign_in_button -> {
                signin()
            }
        }
    }

    private fun signin() {
        var signinIntent : Intent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient)
        startActivityForResult(signinIntent,MainActivity.CODE_SIGNIN_GOOGLE)
    }

    override fun sendSMS() {
        try{
            val phoneNumber : String = "0497352844"
            val msg : String = "Ne fais pas attention au sms c'est u test d'envoie automatique.SORRY !!"

            var smsManager : SmsManager = SmsManager.getDefault()
            smsManager.sendTextMessage(phoneNumber,null,msg,null,null)
            Toast.makeText(activity,getString(R.string.message_sent).plus(" ").plus(phoneNumber),Toast.LENGTH_SHORT).show()
        }catch (e : Exception) {
            e.printStackTrace()
            Log.e("E",e.message)
        }
    }

    override fun resultSignin(result: GoogleSignInResult) {
        Log.i("Test","result is ".plus(result.isSuccess))
        if(result.isSuccess){
            var accou : GoogleSignInAccount = result.signInAccount!!
            Log.i("Test","result : ".plus(accou.displayName))
        }
    }

}