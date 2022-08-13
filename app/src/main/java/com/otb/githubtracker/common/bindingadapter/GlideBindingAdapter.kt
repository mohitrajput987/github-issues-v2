package com.otb.githubtracker.common.bindingadapter

import android.annotation.SuppressLint
import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions

/**
 * Created by Mohit Rajput on 13/08/22.
 */
class GlideBindingAdapter {
    companion object {
        @SuppressLint("CheckResult")
        @JvmStatic
        @BindingAdapter("imageUrl", "placeholder", "isCircular", requireAll = false)
        fun setImageWithPlaceholder(
            imageView: ImageView,
            imageUrl: String?,
            placeholder: Drawable,
            isCircular: Boolean?
        ) {
            val requestManager = Glide.with(imageView.context).load(imageUrl)
                .placeholder(placeholder)
                .apply(if (imageUrl == null) RequestOptions().fitCenter() else RequestOptions().centerCrop())
                .transition(
                    DrawableTransitionOptions.withCrossFade()
                )

            if (isCircular == true) requestManager.circleCrop()
            requestManager.into(imageView)
        }
    }
}