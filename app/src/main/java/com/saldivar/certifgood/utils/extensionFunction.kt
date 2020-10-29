package com.saldivar.certifgood.utils

import android.widget.ImageView
import com.saldivar.certifgood.R
import com.squareup.picasso.Picasso
import java.util.*


fun listNumberRandom(range: IntRange): Int {
    val random = Random()
    return random.nextInt(range.last - range.first)+ range.first
}

fun ImageView.loadByResourcePicaso(url:Int)= Picasso.get().load(url).into(this)
fun ImageView.loadByUrlPicaso(url:String,defecto:Int)= Picasso.get().load(url).error(defecto).into(this)