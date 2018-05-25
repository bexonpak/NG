package org.dt.bexon.ng.impl

import org.dt.bexon.ng.bean.PicBean
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Created by bexon on 2018/05/17.
 */
interface PicService {
    @GET("/photography/photo-of-the-day/_jcr_content/.gallery.{year}-{month}.json")
    fun getJson(@Path("year") year: Int, @Path("month") month: Int): Call<PicBean>
}