package com.filippoengidashet.johnlewis.common

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import javax.inject.Inject

/**
 * @author Filippo Engidashet
 * @version 1.0.0
 * @since Tue, 08/02/2022 at 15:03.
 */
class ImageLoader @Inject constructor() {

    fun load(url: Any, target: ImageView) {
        Glide.with(target.context.applicationContext)
            .load(url)
            .transition(DrawableTransitionOptions.withCrossFade())
            .placeholder(ColorDrawable(Color.parseColor("#cacaca")))
//            .error(ColorDrawable(Color.parseColor("#ffa993")))
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(target)
    }
}
