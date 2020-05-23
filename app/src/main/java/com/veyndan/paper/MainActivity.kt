package com.veyndan.paper

import com.trello.navi2.Event
import com.trello.navi2.component.support.NaviAppCompatActivity
import com.trello.navi2.rx.RxNavi
import org.jetbrains.anko.setContentView

class MainActivity : NaviAppCompatActivity() {

    init {
        RxNavi.observe(this, Event.CREATE)
                .subscribe {
                    val mainActivityUI = MainActivityUI()
                    mainActivityUI.setContentView(this)
                }
    }
}
