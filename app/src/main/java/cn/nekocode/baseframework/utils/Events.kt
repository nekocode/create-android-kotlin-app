package cn.nekocode.baseframework.utils

import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.AbsListView
import android.widget.EditText
import cn.nekocode.baseframework.model.Weather
import cn.nekocode.baseframework.rest.REST
import rx.Observable
import rx.android.schedulers.AndroidSchedulers
import rx.lang.kotlin.PublishSubject
import rx.subjects.BehaviorSubject
import rx.subjects.PublishSubject
import kotlin.properties.Delegates

/**
 * Created by nekocode on 2015/8/13.
 */
class Events {
    companion object {
        public fun text(editText: EditText): Observable<String> {
            val behaviourSubject: BehaviorSubject<String> = BehaviorSubject.create(editText.getText().toString());
            editText.addTextChangedListener(object : TextWatcher {
                override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                }

                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                }

                override fun afterTextChanged(s: Editable) {
                    behaviourSubject.onNext(s.toString())
                }
            })
            return behaviourSubject
        }

        public fun click(view: View): Observable<Any> {
            val subject: PublishSubject<Any> = PublishSubject.create()
            view.setOnClickListener({subject.onNext(Any())})
            return subject
        }
    }
}

