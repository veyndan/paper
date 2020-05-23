package com.veyndan.paper

import android.content.Context
import android.util.TypedValue
import android.view.ViewManager
import de.hdodenhof.circleimageview.CircleImageView
import org.jetbrains.anko.custom.ankoView

fun Context.attribute(value: Int): TypedValue {
    val ret = TypedValue()
    theme.resolveAttribute(value, ret, true)
    return ret
}

fun Context.attrAsDimen(value: Int): Int {
    return TypedValue.complexToDimensionPixelSize(attribute(value).data, resources.displayMetrics)
}

inline fun ViewManager.circleImageView(init: CircleImageView.() -> Unit): CircleImageView {
    return ankoView({ CircleImageView(it) }, theme = 0, init = init)
}
