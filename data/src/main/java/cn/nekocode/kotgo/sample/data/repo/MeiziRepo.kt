package cn.nekocode.kotgo.sample.data.repo

import cn.nekocode.kotgo.sample.data.dto.Meizi
import cn.nekocode.kotgo.sample.data.exception.GankServiceException
import cn.nekocode.kotgo.sample.data.service.GankService
import com.orhanobut.hawk.Hawk
import rx.Observable
import rx.schedulers.Schedulers

/**
 * Created by nekocode on 2016/1/13.
 */
object MeiziRepo {

    // Bussines Logic
    fun getMeizis(count: Int, pageNum: Int): Observable<List<Meizi>> =
            GankService.api.getMeizi(count, pageNum)
                    .subscribeOn(Schedulers.io())
                    .map {
                        Hawk.put("meizis", it.results)
                        it.results
                    }
                    .onErrorResumeNext {
                        // Fetch data from local cache
                        val meiziList: List<Meizi> = Hawk.get("meizis")
                                ?: throw GankServiceException(it.message)
                        Observable.just(meiziList)
                    }

}