package cn.nekocode.template.screen.main

import android.os.Bundle
import cn.nekocode.itempool.Item
import cn.nekocode.itempool.ItemPool
import cn.nekocode.template.base.BasePresenter
import cn.nekocode.template.data.DO.Meizi
import cn.nekocode.template.data.service.GankService
import cn.nekocode.template.item.MeiziItem
import com.evernote.android.state.State
import com.evernote.android.state.StateSaver
import com.trello.rxlifecycle2.kotlin.bindToLifecycle
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.*

/**
 * @author nekocode (nekocode.cn@gmail.com)
 */
class MainPresenter : BasePresenter<Contract.View>(), Contract.Presenter {
    @State
    var list: ArrayList<Meizi>? = null
    var itemPool = ItemPool()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        StateSaver.restoreInstanceState(this, savedInstanceState)

        itemPool.addType(MeiziItem::class.java)
        itemPool.onEvent(MeiziItem::class.java) { event ->
            val meizi = (event.data as MeiziItem.VO).DO as Meizi
            when (event.action) {
                Item.EVENT_ITEM_CLICK -> {
                    gotoPage2(context, meizi)
                }
            }
        }
    }

    override fun onViewCreated(view: Contract.View, savedInstanceState: Bundle?) {
        if (list == null) {
            GankService.getMeizis(50, 1)
        } else {
            Observable.just(list!!)
        }
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .map { meizis ->
                    list = meizis
                    meizis.map { MeiziItem.VO.fromMeizi(it) }
                }
                .bindToLifecycle(this)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    itemPool.clear()
                    itemPool.addAll(it)
                    view.setAdapter(itemPool.adapter)
                }, this::onError)
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        StateSaver.saveInstanceState(this, outState ?: return)
    }
}