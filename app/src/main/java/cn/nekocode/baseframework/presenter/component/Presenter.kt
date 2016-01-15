package cn.nekocode.baseframework.presenter.component

/**
 * Created by nekocode on 2015/11/20.
 * inspired by: https://github.com/android10/Android-CleanArchitecture
 */
interface Presenter {
    fun resume()
    fun pause()
    fun destory()
}