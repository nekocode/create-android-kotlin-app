package cn.nekocode.kotgo.sample.data.repo

import cn.nekocode.kotgo.sample.data.DO.Meizi
import cn.nekocode.kotgo.sample.data.exception.GankServiceException
import cn.nekocode.kotgo.sample.data.service.Api.Gank
import com.orhanobut.hawk.Hawk
import rx.Observable
import rx.schedulers.Schedulers

/**
 * @author nekocode (nekocode.cn@gmail.com)
 */
object MeiziRepo {

    // Bussines Logic
    fun getMeizis(count: Int, pageNum: Int): Observable<List<Meizi>> =
            Gank.API.getMeizi(count, pageNum)
                    .subscribeOn(Schedulers.io())
                    .map {
                        if (pageNum == 1) Hawk.put("meizis", it.results)
                        it.results
                    }
                    .onErrorResumeNext {
                        if (pageNum != 1) throw GankServiceException(it.message)

                        // Fetch data from local cache
                        val meiziList: List<Meizi> = Hawk.get("meizis")
                                ?: throw GankServiceException(it.message)
                        Observable.just(meiziList)
                    }

}