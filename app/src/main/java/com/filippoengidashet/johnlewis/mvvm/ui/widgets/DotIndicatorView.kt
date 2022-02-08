package com.filippoengidashet.johnlewis.mvvm.ui.widgets

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.viewpager.widget.ViewPager
import com.filippoengidashet.johnlewis.R
import com.filippoengidashet.johnlewis.utils.Utils

/**
 * @author Filippo Engidashet
 * @version 1.0.0
 * @since Tue, 08/02/2022 at 16:31.
 */
class DotIndicatorView : LinearLayoutCompat {

    private var clickListener: OnClickListener? = null

    constructor(context: Context) : super(context) {
        doInit()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        doInit()
    }

    constructor(
        context: Context, attrs: AttributeSet, defStyleAttr: Int
    ) : super(context, attrs, defStyleAttr) {
        doInit()
    }

    private fun doInit() {
        orientation = HORIZONTAL
    }

    fun setOnClickListener(listener: OnClickListener) {
        clickListener = listener
    }

    fun setViewPager(pager: ViewPager) {
        pager.adapter?.let {
            pager.addOnPageChangeListener(object : ViewPager.SimpleOnPageChangeListener() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    setSelectedPosition(position)
                }
            })
            val count = it.count
            createIndicators(count)
            if (count > 0) setSelectedPosition(0)
        }
    }

    fun createIndicators(count: Int) {
        removeAllViews()
        val size = Utils.toPx(12f)
        for (i in 0 until count) {
            val params = LayoutParams(size, size)
            params.leftMargin = if (i == 0) 0 else size
            val dot = View(context)
            dot.setBackgroundResource(R.drawable.selector_indicator)
            dot.setOnClickListener { v ->
                val position = indexOfChild(v)
                setSelectedPosition(position)
                clickListener?.onClick(position)
            }
            addView(dot, i, params)
        }
    }

    fun setSelectedPosition(position: Int) {
        val count = childCount
        for (i in 0 until count) {
            getChildAt(i).isSelected = i == position
        }
    }

    interface OnClickListener {
        fun onClick(position: Int)
    }
}
