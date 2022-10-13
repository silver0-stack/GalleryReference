package com.android.handstudio.gallery.divider

import android.R
import android.content.res.Resources
import android.graphics.Canvas
import android.graphics.Rect
import androidx.recyclerview.widget.RecyclerView.ItemDecoration
import android.graphics.drawable.Drawable
import android.view.View
import androidx.recyclerview.widget.RecyclerView

/**
 * Created by woong on 2015. 9. 15..
 */
class GridDividerDecoration(resources: Resources, layout: Int) : ItemDecoration() {
    private val mDivider: Drawable
    private val mInsets: Int

    init {
        mDivider = resources.getDrawable(layout)
        mInsets = 1
    }

    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        drawVertical(c, parent)
        drawHorizontal(c, parent)
    }

    /** Draw dividers at each expected grid interval  */
    fun drawVertical(c: Canvas?, parent: RecyclerView) {
        if (parent.childCount == 0) return
        val left = parent.paddingLeft
        val right = parent.width - parent.paddingRight
        val child = parent.getChildAt(0)
        if (child.height == 0) return
        val params = child.layoutParams as RecyclerView.LayoutParams
        var top = child.bottom + params.bottomMargin + mInsets
        var bottom = top + mDivider.intrinsicHeight
        val parentBottom = parent.height - parent.paddingBottom
        while (bottom < parentBottom) {
            mDivider.setBounds(left, top, right, bottom)
            mDivider.draw(c!!)
            top += mInsets + params.topMargin + child.height + params.bottomMargin + mInsets
            bottom = top + mDivider.intrinsicHeight
        }
    }

    /** Draw dividers to the right of each child view  */
    fun drawHorizontal(c: Canvas?, parent: RecyclerView) {
        val top = parent.paddingTop
        val bottom = parent.height - parent.paddingBottom
        val childCount = parent.childCount
        for (i in 0 until childCount) {
            val child = parent.getChildAt(i)
            val params = child.layoutParams as RecyclerView.LayoutParams
            val left = child.right + params.rightMargin + mInsets
            val right = left + mDivider.intrinsicWidth
            mDivider.setBounds(left, top, right, bottom)
            mDivider.draw(c!!)
        }
    }

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        //We can supply forced insets for each item view here in the Rect
        outRect[mInsets, mInsets, mInsets] = mInsets
    }

    companion object {
        private val ATTRS = intArrayOf(R.attr.listDivider)
    }
}