package org.dt.bexon.ng.holder

import android.support.constraint.ConstraintLayout
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import org.dt.bexon.ng.R

/**
 * Created by bexon on 2018/05/17.
 */
class PicListViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    var title: TextView = view.findViewById(R.id.title) as TextView
    var imageView: ImageView = view.findViewById(R.id.image_view) as ImageView
    var constraintLayout: ConstraintLayout = view.findViewById(R.id.item_constraint_layout) as ConstraintLayout
}