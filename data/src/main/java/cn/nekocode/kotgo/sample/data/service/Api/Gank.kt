package cn.nekocode.kotgo.sample.data.service.Api

import cn.nekocode.kotgo.sample.data.DO.Meizi
import cn.nekocode.kotgo.sample.data.service.GankService
import retrofit2.http.GET
import retrofit2.http.Path
import rx.Observable

/**
 * Created by nekocode on 16/8/26.
 */
internal interface Gank {
    companion object {
        val API: Gank = GankService.REST_ADAPTER.create(Gank::class.java)
    }

    @GET("{count}/{pageNum}")
    fun getMeizi(@Path("count") count: Int, @Path("pageNum") pageNum: Int)
            : Observable<GankService.ResponseWrapper<Meizi>>
}