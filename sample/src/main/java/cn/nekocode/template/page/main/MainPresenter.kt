package cn.nekocode.template.page.main

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
import java.util.*

/**
 * @author nekocode (nekocode.cn@gmail.com)
 */
class MainPresenter : BasePresenter<Contract.View>(), Contract.Presenter {
    @State
    var meizis: ArrayList<Meizi>? = null
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

    override fun onViewCreated(view: Contract.View?, savedInstanceState: Bundle?) {
        view?.setItemPool(itemPool)

        /*
          Fetch data
         */
        if (meizis == null) {
            GankService.getMeizis(50, 1)
        } else {
            Observable.just(meizis!!)
        }
                .map { list -> list.map { MeiziItem.VO.fromMeizi(it) } }
                .bindToLifecycle(this)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    itemPool.clear()
                    itemPool.addAll(it)
                    itemPool.notifyDataSetChanged()
                }
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        StateSaver.saveInstanceState(this, outState ?: return)
    }
}