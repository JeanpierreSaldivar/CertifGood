package com.saldivar.certifgood.utils

import java.util.*


fun listNumberRandom(range: IntRange): Int {
    val random = Random()
    return random.nextInt(range.last - range.first)+ range.first
}