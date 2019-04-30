package com.example.rxbingingapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Switch
import com.jakewharton.rxbinding3.view.clicks
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Function4
import io.reactivex.functions.Function5
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button.clicks()
            .flatMap {
                Observable.zip(
                    createObservableFromSwitch(switch1),
                    createObservableFromSwitch(switch2),
                    createObservableFromSwitch(switch3),
                    createObservableFromSwitch(switch4),
                    Function4<Switch, Switch, Switch, Switch, List<Boolean>> { t1, t2, t3, t4 ->
                        listOf(t1.isChecked, t2.isChecked, t3.isChecked, t4.isChecked)
                    }
                )
            }
            .subscribe({
                Log.d("list of switch", "$it")
            }, Throwable::printStackTrace)

    }

    fun createObservableFromSwitch(switch: Switch) = Observable.create<Switch> { emitter ->
        emitter.onNext(switch)

        switch.setOnCheckedChangeListener { buttonView, isChecked ->
            emitter.onNext(switch)
        }
    }
}
