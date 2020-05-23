package com.veyndan.paper

import android.support.constraint.ConstraintSet.PARENT_ID
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import de.hdodenhof.circleimageview.CircleImageView
import org.jetbrains.anko.*
import org.jetbrains.anko.constraint.layout.ConstraintSetBuilder.Side.*
import org.jetbrains.anko.constraint.layout.applyConstraintSet
import org.jetbrains.anko.constraint.layout.constraintLayout

class ItemUi : AnkoComponent<View> {

    lateinit var profile: CircleImageView
    lateinit var name: TextView
    lateinit var date: TextView
    lateinit var image: ImageView
    lateinit var text: TextView

    override fun createView(ui: AnkoContext<View>): View = ui.apply {
        constraintLayout {
            lparams(width = matchParent, height = wrapContent)

            profile = circleImageView {
                id = View.generateViewId()
            }

            name = textView {
                id = View.generateViewId()
                textAppearance = R.style.TextAppearance_AppCompat_Body1
            }

            date = textView {
                id = View.generateViewId()
                textAppearance = R.style.TextAppearance_AppCompat_Caption
            }

            image = imageView {
                id = View.generateViewId()
            }

            text = textView {
                id = View.generateViewId()
                textAppearance = R.style.TextAppearance_AppCompat_Body1
            }

            applyConstraintSet {
                profile {
                    width = dip(40)
                    height = dip(40)

                    connect(
                            LEFT to LEFT of PARENT_ID margin dip(16),
                            TOP to TOP of PARENT_ID margin dip(16)
                    )
                }

                name {
                    width = 0
                    height = 0

                    connect(
                            LEFT to RIGHT of profile margin dip(16),
                            RIGHT to RIGHT of PARENT_ID margin dip(16),
                            TOP to TOP of profile,
                            BOTTOM to TOP of date
                    )
                }

                date {
                    width = 0
                    height = 0

                    connect(
                            LEFT to LEFT of name,
                            RIGHT to RIGHT of name,
                            TOP to BOTTOM of name,
                            BOTTOM to BOTTOM of profile
                    )
                }

                image {
                    width = 0
                    height = wrapContent

                    connect(
                            LEFT to LEFT of PARENT_ID,
                            RIGHT to RIGHT of PARENT_ID,
                            TOP to BOTTOM of profile margin dip(16)
                    )
                }

                text {
                    width = 0
                    height = wrapContent

                    connect(
                            LEFT to LEFT of PARENT_ID margin dip(16),
                            RIGHT to RIGHT of PARENT_ID margin dip(16),
                            TOP to BOTTOM of image margin dip(16),
                            BOTTOM to BOTTOM of PARENT_ID margin dip(16)
                    )
                }
            }
        }
    }.view
}
