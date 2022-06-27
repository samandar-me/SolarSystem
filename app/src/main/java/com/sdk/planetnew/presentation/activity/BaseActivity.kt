package com.sdk.planetnew.presentation.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.sdk.planetnew.data.util.Constants
import com.sdk.planetnew.R

abstract class BaseActivity : AppCompatActivity() {

    private lateinit var context: Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        context = this
    }

    fun startAnimation(view: View) {
        val animation: Animation = AnimationUtils.loadAnimation(context, R.anim.top)
        view.startAnimation(animation)
    }

    fun changeWindow(window: Window) {
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = ContextCompat.getColor(this, R.color.orange)
    }

    fun intent(activity: Activity) {
        object : CountDownTimer(3000, 1000) {
            override fun onTick(millisUntilFinished: Long) {}

            override fun onFinish() {
                startActivity(Intent(activity, MainActivity::class.java))
                finish()
            }
        }.start()
    }

    fun goToTelegram() {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(Constants.USER_NAME)
        val intentChooser = Intent.createChooser(intent, "Launch Telegram")
        startActivity(intentChooser)
    }

    fun goToChannel() {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(Constants.CHANNEL_NAME)
        val intentChooser = Intent.createChooser(intent, "Launch Telegram")
        startActivity(intentChooser)
    }

    fun myOtherApps() {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(Constants.MY_APPS)
        val intentChooser = Intent.createChooser(intent, "Launch Play Market")
        startActivity(intentChooser)
    }

    fun shareThisApp(packageName: String) {
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "text/plain"
        intent.putExtra(Intent.EXTRA_TEXT, "${Constants.URL}$packageName")
        startActivity(Intent.createChooser(intent, "Share Via"))
    }

    fun intentToPlayMarket(packageName: String) {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse("${Constants.URL}$packageName")
        startActivity(intent)
    }

}