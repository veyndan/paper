package com.veyndan.paper

import android.support.design.widget.AppBarLayout
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.MenuItem
import android.view.View
import com.squareup.moshi.KotlinJsonAdapterFactory
import com.squareup.moshi.Moshi
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import org.jetbrains.anko.AnkoComponent
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.appcompat.v7.titleResource
import org.jetbrains.anko.appcompat.v7.toolbar
import org.jetbrains.anko.backgroundColor
import org.jetbrains.anko.design.coordinatorLayout
import org.jetbrains.anko.design.themedAppBarLayout
import org.jetbrains.anko.matchParent
import org.jetbrains.anko.recyclerview.v7.recyclerView
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import timber.log.Timber

class MainActivityUI : AnkoComponent<MainActivity> {

    companion object {

        private val moshi = Moshi.Builder()
                .add(KotlinJsonAdapterFactory())
                .build()

        private val jsonAdapter = moshi.adapter(Query::class.java)

        private val retrofit = Retrofit.Builder()
                .baseUrl("http://192.168.0.10:8080/")
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .build()

        private val service = retrofit.create(PaperService::class.java)

        private fun updateAdapter(recyclerView: RecyclerView, query: Query): Disposable = service.items(jsonAdapter.toJson(query))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ items ->
                    recyclerView.adapter = FeedAdapter(items)
                }, { t -> Timber.e(t, "Something went wrong.") })
    }

    override fun createView(ui: AnkoContext<MainActivity>): View = ui.apply {
        coordinatorLayout {
            lparams(width = matchParent, height = matchParent)
            clipChildren = false
            clipToPadding = false
            fitsSystemWindows = true

            val recyclerView = recyclerView {
                id = View.generateViewId()
                backgroundColor = ctx.getColor(android.R.color.white)
                layoutManager = LinearLayoutManager(ctx)
                addItemDecoration(DividerItemDecoration(ctx, DividerItemDecoration.VERTICAL))
                adapter = FeedAdapter(listOf())
            }.lparams(width = matchParent, height = matchParent) {
                behavior = AppBarLayout.ScrollingViewBehavior()
            }

            themedAppBarLayout(R.style.AppTheme_AppBarOverlay) {

                toolbar {
                    id = View.generateViewId()
                    popupTheme = R.style.AppTheme_PopupOverlay
                    titleResource = R.string.app_name

                    val defaultQuery = Query()
                    val (defaultFilterTexts, defaultFilterUsernames, defaultSort) = defaultQuery

                    menu.apply {
                        lateinit var filterTextValueMenuItems: List<MenuItem>
                        lateinit var filterUsernameValueMenuItems: List<MenuItem>
                        lateinit var sortDateAscMenuItem: MenuItem
                        lateinit var sortDateDescMenuItem: MenuItem

                        fun updateQuery() = Query(
                                filterTexts = filterTextValueMenuItems
                                        .filter { it.isChecked }
                                        .map { FilterText(it.title as String) },
                                filterUsernames = filterUsernameValueMenuItems
                                        .filter { it.isChecked }
                                        .map { FilterUsername(it.title as String) },
                                sort = Sort(when {
                                    sortDateAscMenuItem.isChecked -> SortOrder.ASCENDING
                                    sortDateDescMenuItem.isChecked -> SortOrder.DESCENDING
                                    else -> error("Unknown sort")
                                })
                        )

                        subMenu(title = ctx.getString(R.string.action_filter)) {
                            item {
                                iconRes = R.drawable.ic_filter_list_black_24dp
                                showAsAction = MenuItem.SHOW_AS_ACTION_IF_ROOM
                            }
                            val menuGroupFilterTextId = View.generateViewId()
                            subMenu(groupId = menuGroupFilterTextId, title = "Text", checkable = true) {
                                filterTextValueMenuItems = listOf("birthday", "brother", "ovo")
                                        .map { text ->
                                            menuItem(groupId = menuGroupFilterTextId, title = text) {
                                                isChecked = defaultFilterTexts.map(FilterText::text).contains(text)
                                                onClick {
                                                    isChecked = !isChecked
                                                    updateAdapter(recyclerView, updateQuery())
                                                    true
                                                }
                                            }
                                        }
                            }
                            val menuGroupFilterUsernameId = View.generateViewId()
                            subMenu(groupId = menuGroupFilterUsernameId, title = "Username", checkable = true) {
                                filterUsernameValueMenuItems = listOf("champagnepapi", "offsetyrn", "ovo40", "partynextdoor", "quavohuncho", "yrntakeoff")
                                        .map { username ->
                                            menuItem(groupId = menuGroupFilterUsernameId, title = username) {
                                                isChecked = defaultFilterUsernames.map(FilterUsername::username).contains(username)
                                                onClick {
                                                    isChecked = !isChecked
                                                    updateAdapter(recyclerView, updateQuery())
                                                    true
                                                }
                                            }
                                        }
                            }
                        }
                        subMenu(title = ctx.getString(R.string.action_sort)) {
                            item {
                                iconRes = R.drawable.ic_sort_black_24dp
                                showAsAction = MenuItem.SHOW_AS_ACTION_IF_ROOM
                            }
                            val menuGroupSortDateId = View.generateViewId()
                            subMenu(groupId = menuGroupSortDateId, title = "Date", checkable = true, exclusive = true) {
                                sortDateAscMenuItem = menuItem(groupId = menuGroupSortDateId, title = "Ascending") {
                                    onClick {
                                        isChecked = !isChecked
                                        updateAdapter(recyclerView, updateQuery())
                                        true
                                    }
                                }
                                sortDateDescMenuItem = menuItem(groupId = menuGroupSortDateId, title = "Descending") {
                                    onClick {
                                        isChecked = !isChecked
                                        updateAdapter(recyclerView, updateQuery())
                                        true
                                    }
                                }
                                when (defaultSort.date) {
                                    SortOrder.ASCENDING -> sortDateAscMenuItem.isChecked = true
                                    SortOrder.DESCENDING -> sortDateDescMenuItem.isChecked = true
                                }
                            }
                        }

                        updateAdapter(recyclerView, updateQuery())
                    }
                }.lparams(width = matchParent, height = matchParent)
            }.lparams(width = matchParent, height = ctx.attrAsDimen(android.R.attr.actionBarSize))
        }
    }.view
}
