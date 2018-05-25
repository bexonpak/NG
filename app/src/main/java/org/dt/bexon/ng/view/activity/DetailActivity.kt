package org.dt.bexon.ng.view.activity

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.graphics.Palette
import android.text.Html
import android.util.Log
import android.view.View
import android.view.WindowManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_detail.*
import org.dt.bexon.ng.R
import org.dt.bexon.ng.bean.Item

class DetailActivity : AppCompatActivity() {

    private var json: String = ""
    private lateinit var item: Item
    private lateinit var bitmap: Bitmap

    override fun onCreate(savedInstanceState: Bundle?) {
        setSystemBar()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        getData()
        setViews()
    }

    //    设置系统Bar
    private fun setSystemBar() {
        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION)
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = Color.parseColor("#50000000")
    }

    //    设置View
    private fun setViews() {
//        图片
        val imageURL: String = if (item.yourShot) {
            item.url + item.originalUrl
        } else {
            item.url
        }
        Glide.with(this)
                .asBitmap()
                .load(imageURL)
                .listener(object : RequestListener<Bitmap> {
                    override fun onResourceReady(resource: Bitmap?, model: Any?, target: Target<Bitmap>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                        bitmap = resource!!
                        setColor()
                        return false
                    }

                    override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Bitmap>?, isFirstResource: Boolean): Boolean {
                        Snackbar.make(detail_constraint_layout, "load failed.", Snackbar.LENGTH_INDEFINITE).show()
                        detail_progress_bar.visibility = View.GONE
                        return false
                    }

                })
                .into(detail_image_view)
//        填入内容
        detail_title.text = item.title
        detail_alt_text.text = item.altText
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            detail_caption.text = Html.fromHtml(item.caption, Html.FROM_HTML_MODE_COMPACT)
        } else {
            detail_caption.text = Html.fromHtml(item.caption)
        }
        detail_credit.text = "Photograph by ${item.credit}"
        detail_publish_date.text = item.publishDate
    }

    private fun setColor() {
        Palette.from(bitmap).generate { palette ->
            val swatch = palette.darkMutedSwatch
            if (swatch != null) {
                detail_constraint_layout.setBackgroundColor(swatch.rgb)
                detail_title.setTextColor(swatch.titleTextColor)
                detail_publish_date.setTextColor(swatch.bodyTextColor)
                detail_alt_text.setTextColor(swatch.bodyTextColor)
                detail_caption.setTextColor(swatch.bodyTextColor)
                detail_credit.setTextColor(swatch.titleTextColor)
            } else {
                Log.e("color", "the swatch is null.")
            }
            handler.sendEmptyMessage(0)
        }
    }

    //    获取解释json数据
    private fun getData() {
        json = intent.extras.get("item") as String? ?: ""
        val gson = Gson()
        item = gson.fromJson(json, Item::class.java)
    }

    @SuppressLint("HandlerLeak")
    private var handler: Handler = object : Handler() {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            when (msg.what) {
                0 -> {
                    detail_progress_bar.visibility = View.GONE
                    detail_scroll_view.visibility = View.VISIBLE
                }
            }
        }
    }

}
