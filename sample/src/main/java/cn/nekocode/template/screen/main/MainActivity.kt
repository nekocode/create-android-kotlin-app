package cn.nekocode.template.screen.main

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import cn.nekocode.template.R
import cn.nekocode.template.base.BaseActivity
import kotlinx.android.synthetic.main.activity_main.*

/**
 * @author nekocode (nekocode.cn@gmail.com)
 */
class MainActivity : BaseActivity(), Contract.View {
    var presenter: Contract.Presenter? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        title = "Meizi List"
        recyclerView.layoutManager = LinearLayoutManager(this)
    }

    override fun onCreatePresenter(presenterFactory: PresenterFactory) {
        presenter = presenterFactory.createOrGet(MainPresenter::class.java)
    }

    override fun setAdapter(adapter: RecyclerView.Adapter<RecyclerView.ViewHolder>) {
        recyclerView.adapter = adapter
    }
}
