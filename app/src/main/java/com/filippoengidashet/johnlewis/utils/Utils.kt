package com.filippoengidashet.johnlewis.utils

import android.content.Context
import android.content.res.Resources
import android.util.Patterns
import android.util.TypedValue
import android.view.View
import android.view.inputmethod.InputMethodManager

/**
 * @author Filippo Engidashet
 * @version 1.0.0
 * @since Tue, 08/02/2022 at 15:36.
 */
object Utils {

    @JvmStatic
    fun resolveURL(url: String): String {
        return if (Patterns.WEB_URL.matcher(url).matches()) url
        else "https:$url"
    }

    fun toPx(size: Float): Int {
        return Math.round(
            TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, size, Resources.getSystem().displayMetrics
            )
        )
    }

    fun hideKeyboard(context: Context, view: View) {
        val imm: InputMethodManager = context.getSystemService(
            Context.INPUT_METHOD_SERVICE
        ) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }
}
