package org.dt.bexon.ng.view.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import org.dt.bexon.ng.R


/**
 * Created by bexon on 2018/05/18.
 */
class SplashActivity : AppCompatActivity() {

    private var mHandler: Handler? = null

    private val mRunnable = {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        overridePendingTransition(R.anim.hold, R.anim.fade)
        finish()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        mHandler = Handler()
    }

    override fun onResume() {
        super.onResume()
        mHandler!!.postDelayed(mRunnable, DELAY_MILLISECONDS)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        removeCallbacks()
    }

    override fun onStop() {
        super.onStop()
        removeCallbacks()
    }

    private fun removeCallbacks() {
        if (mHandler != null) {
            mHandler!!.removeCallbacks(mRunnable)
        }
    }

    companion object {
        const val DELAY_MILLISECONDS: Long = 600
    }

}