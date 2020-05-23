package com.veyndan.paper

import android.widget.ImageView
import kotlin.math.roundToInt

// This should ultimately not be needed as my database should send the height and width values of the image to me.
fun ImageView.aspectRatio(width: Int, height: Int, aspect: Aspect) {
    post {
        when (aspect) {
            Aspect.HEIGHT -> TODO()
            Aspect.WIDTH -> {
                val aspectRatio = (height.toDouble() / width.toDouble())
                val imageWidth = this.width.toDouble()
                layoutParams.height = (imageWidth * aspectRatio).roundToInt()
                requestLayout()
            }
        }
    }
}

// TODO Is this the correct name?
enum class Aspect {
    HEIGHT, WIDTH
}
