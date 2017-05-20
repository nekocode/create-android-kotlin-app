package cn.nekocode.template.data.service

import cn.nekocode.template.data.DO.Meizi
import cn.nekocode.template.data.exception.GankServiceException
import cn.nekocode.template.data.api.GankApi
import io.paperdb.Paper
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers

/**
 * @author nekocode (nekocode.cn@gmail.com)
 */
object GankService {

    fun getMeizis(count: Int, pageNum: Int): Observable<ArrayList<Meizi>> =
            GankApi.IMPL.getMeizis(count, pageNum)
                    .subscribeOn(Schedulers.io())
                    .map {
                        Paper.book().write("meizis-$pageNum", it.results)
                        it.results
                    }
                    .onErrorResumeNext { err: Throwable ->
                        val list: ArrayList<Meizi> = Paper.book().read("meizis-$pageNum")
                                ?: throw GankServiceException(err.message)
                        Observable.just(list)
                    }

}