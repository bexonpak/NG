package org.dt.bexon.ng.adapter

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.gson.Gson
import org.dt.bexon.ng.R
import org.dt.bexon.ng.bean.Item
import org.dt.bexon.ng.bean.PicBean
import org.dt.bexon.ng.holder.PicListViewHolder
import org.dt.bexon.ng.view.activity.DetailActivity


/**
 * Created by bexon on 2018/05/17.
 */
class PicListRecyclerViewAdapter(private var context: Context, picBean: PicBean) : RecyclerView.Adapter<PicListViewHolder>() {

    private var list: List<Item> = picBean.items

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PicListViewHolder {
        val view: View = LayoutInflater.from(context).inflate(R.layout.pic_item, parent, false)
        return PicListViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: PicListViewHolder, position: Int) {
        holder.title.text = list[position].title
        val options = RequestOptions()
                .centerCrop()
                .placeholder(R.color.placeholder)
        val imageURL: String = if (list[position].yourShot) {
            list[position].url + list[position].sizes.x800
        } else {
            list[position].url
        }
        Glide.with(context)
                .load(imageURL)
                .apply(options)
                .into(holder.imageView)
        holder.constraintLayout.setOnClickListener {
            val intent = Intent(context, DetailActivity::class.java)
            val gson = Gson()
            intent.putExtra("item", gson.toJson(list[position]))
            context.startActivity(intent)
        }
    }

    init {
        notifyDataSetChanged()
    }

}