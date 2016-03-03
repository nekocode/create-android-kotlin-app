package cn.nekocode.baseframework.sample.data.model

import cn.nekocode.baseframework.sample.data.dto.Meizi
import cn.nekocode.baseframework.sample.data.service.Net
import com.orhanobut.hawk.Hawk
import rx.Notification
import rx.Observable
import rx.schedulers.Schedulers

/**
 * Created by nekocode on 2016/1/13.
 */
object MeiziModel {

    // Bussines Logic
    fun getMeizis(count: Int, pageNum: Int): Observable<List<Meizi>> =
            Net.api.getMeizi(count, pageNum)
                    .subscribeOn(Schedulers.io())
                    .map { it.results }
                    .doOnEach {
                        if(it.kind == Notification.Kind.OnNext) {
                            // Cache weather to local cache
                            Hawk.put("meizis", it.value)
                        }
                    }
                    .onErrorResumeNext {
                        // Fetech weather from local cache
                        val meiziList: List<Meizi>? = Hawk.get("meizis")
                        Observable.just(meiziList)
                    }

}