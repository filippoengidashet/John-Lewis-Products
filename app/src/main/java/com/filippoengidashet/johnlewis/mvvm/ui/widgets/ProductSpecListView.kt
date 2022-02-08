package com.filippoengidashet.johnlewis.mvvm.ui.widgets

import android.content.Context
import android.util.AttributeSet
import android.widget.TextView
import androidx.appcompat.widget.LinearLayoutCompat
import com.filippoengidashet.johnlewis.R
import com.filippoengidashet.johnlewis.mvvm.data.model.ProductSpec

/**
 * @author Filippo Engidashet
 * @version 1.0.0
 * @since Tue, 08/02/2022 at 18:04.
 */
class ProductSpecListView : LinearLayoutCompat {

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
        orientation = VERTICAL
    }

    fun setSpecs(specs: List<ProductSpec>) {
        removeAllViews()
        for(i in specs.indices) {
            val spec = specs[i]

            val view = inflate(context, R.layout.item_product_spec_layout, null)

            val textTitle = view.findViewById<TextView>(R.id.text_title)
            val textValue = view.findViewById<TextView>(R.id.text_value)

            textTitle.text = spec.name
            textValue.text = spec.value

            addView(view, i)
        }
    }
}
