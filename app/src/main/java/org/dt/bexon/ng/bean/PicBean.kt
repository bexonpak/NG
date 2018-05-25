package org.dt.bexon.ng.bean

import com.google.gson.annotations.SerializedName


/**
 * Created by bexon on 2018/05/17.
 */


data class PicBean(
        @SerializedName("galleryTitle") val galleryTitle: String,
        @SerializedName("previousEndpoint") val previousEndpoint: String,
        @SerializedName("nextEndpoint") val nextEndpoint: String,
        @SerializedName("items") val items: List<Item>
)

data class Item(
        @SerializedName("title") val title: String,
        @SerializedName("caption") val caption: String,
        @SerializedName("credit") val credit: String,
        @SerializedName("profileUrl") val profileUrl: String,
        @SerializedName("altText") val altText: String,
        @SerializedName("full-path-url") val fullPathUrl: String,
        @SerializedName("url") val url: String,
        @SerializedName("originalUrl") val originalUrl: String,
        @SerializedName("aspectRatio") val aspectRatio: Double,
        @SerializedName("sizes") val sizes: Sizes,
        @SerializedName("internal") val internal: Boolean,
        @SerializedName("pageUrl") val pageUrl: String,
        @SerializedName("publishDate") val publishDate: String,
        @SerializedName("yourShot") val yourShot: Boolean,
        @SerializedName("social") val social: Social,
        @SerializedName("guid") val guid: String,
        @SerializedName("height") val height: Int,
        @SerializedName("width") val width: Int
)

data class Sizes(
        @SerializedName("240") val x240: String,
        @SerializedName("320") val x320: String,
        @SerializedName("500") val x500: String,
        @SerializedName("640") val x640: String,
        @SerializedName("800") val x800: String,
        @SerializedName("1024") val x1024: String,
        @SerializedName("1600") val x1600: String,
        @SerializedName("2048") val x2048: String
)

data class Social(
        @SerializedName("og:title") val ogtitle: String,
        @SerializedName("og:description") val ogdescription: String,
        @SerializedName("twitter:site") val twittersite: String
)