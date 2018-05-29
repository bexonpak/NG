package org.dt.bexon.ng.view.activity

import android.annotation.SuppressLint
import android.app.Activity
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.support.design.widget.Snackbar
import android.support.v4.app.ActivityCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.view.WindowManager
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.activity_main.*
import org.dt.bexon.ng.R
import org.dt.bexon.ng.adapter.PicListRecyclerViewAdapter
import org.dt.bexon.ng.bean.PicBean
import org.dt.bexon.ng.bean.URLBean
import org.dt.bexon.ng.impl.PicService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*


class MainActivity : AppCompatActivity() {

    private var picBean: PicBean? = null
    private val REQUEST_EXTERNAL_STORAGE = 1
    private val PERMISSIONS_STORAGE = arrayOf("android.permission.READ_EXTERNAL_STORAGE", "android.permission.WRITE_EXTERNAL_STORAGE")

    override fun onCreate(savedInstanceState: Bundle?) {
//        必须放在onCreate()前面,否则会有View移动出bar动作出现
        setSystemBar()
        super.onCreate(savedInstanceState)
//        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        verifyStoragePermissions(activity = this)
        setContentView(R.layout.activity_main)
        ininData()
    }

    //    设置系统Bar
    private fun setSystemBar() {
//        透明状态栏，透明底部导航栏
//        原生半透明状态栏api，MIUI无法半透明
//        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
//        透明底部导航栏
        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION)
//        取消状态栏透明
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
//        添加Flag把状态栏设为可绘制模式
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
//        设置状态栏透明颜色為透明色#50000000
        window.statusBarColor = Color.parseColor("#50000000")
    }

    //    设置View
    private fun view() {
        val adapter = PicListRecyclerViewAdapter(this, picBean!!)
        pic_list.adapter = adapter
        val layoutManager: LinearLayoutManager = object : LinearLayoutManager(this) {
            override fun canScrollVertically(): Boolean {
                return false
            }
        }
        main_progress_bar.visibility = View.GONE
        pic_list.layoutManager = layoutManager
        gallery_title.text = picBean?.galleryTitle ?: "Title"
    }

    //    获取json数据
    private fun ininData() {
        val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(URLBean.host)
                .build()
        val service: PicService = retrofit.create(PicService::class.java)
        Thread(Runnable {
            try {
                val cal = Calendar.getInstance()
                val year = cal.get(Calendar.YEAR)
                val month = cal.get(Calendar.MONTH) + 1
                picBean = service.getJson(year, month).execute().body()!!
                handler.sendEmptyMessage(0)
            } catch (e: Exception) {
                Snackbar.make(main_relative_layout, e.message.toString(), Snackbar.LENGTH_INDEFINITE).setAction("Refresh", {
                    ininData()
                }).show()
            }
        }).start()
    }

    @SuppressLint("HandlerLeak")
    private var handler: Handler = object : Handler() {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            when (msg.what) {
                0 -> view()
            }
        }
    }

    private fun verifyStoragePermissions(activity: Activity){
        try {
            val permissions: Int = ActivityCompat.checkSelfPermission(activity, "android.permission.WRITE_EXTERNAL_STORAGE")
            if (permissions != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(activity, PERMISSIONS_STORAGE,REQUEST_EXTERNAL_STORAGE)
            }
        } catch (e: Exception) {
            Snackbar.make(detail_constraint_layout, e.message.toString(), Snackbar.LENGTH_LONG).show()
        }
    }
}
