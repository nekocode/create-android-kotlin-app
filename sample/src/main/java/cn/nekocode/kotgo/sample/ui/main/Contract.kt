package cn.nekocode.kotgo.sample.ui.main

/**
 * Created by nekocode on 16/4/9.
 */
interface Contract {
    interface View {
    }

    interface Presenter {
        fun getAdapter(): MeiziListAdapter
    }
}