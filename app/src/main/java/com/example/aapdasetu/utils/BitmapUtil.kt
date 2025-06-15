package com.example.aapdasetu.utils

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import androidx.core.graphics.createBitmap

fun bitmapDescriptorFromVector(context: Context, vectorResId: Int, sizeInDp: Float): BitmapDescriptor {
    val vectorDrawable: Drawable = ContextCompat.getDrawable(context, vectorResId)!!

    val density = context.resources.displayMetrics.density

    val sizePx = (sizeInDp * density).toInt()
    val bitmap = createBitmap(sizePx, sizePx)
    val canvas = Canvas(bitmap)
    vectorDrawable.setBounds(0, 0, canvas.width, canvas.height)
    vectorDrawable.draw(canvas)
    return BitmapDescriptorFactory.fromBitmap(bitmap)
}
