package com.paysera.currencyexchange.bindingAdapter

import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions


@BindingAdapter("load_image")
fun bindingImage(target: ImageView, resource: Drawable) {
    resource.let {
        Glide.with(target.context)
            .load(it)
            .into(target)
    }
}

