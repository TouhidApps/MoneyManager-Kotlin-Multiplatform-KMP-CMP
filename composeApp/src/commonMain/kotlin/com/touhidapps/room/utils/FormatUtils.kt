package com.touhidapps.room.utils

import kotlin.math.pow
import kotlin.math.round


fun Double.roundToTwoDecimals(): Double {
    val factor = 10.0.pow(2)
    return round(this * factor) / factor
}