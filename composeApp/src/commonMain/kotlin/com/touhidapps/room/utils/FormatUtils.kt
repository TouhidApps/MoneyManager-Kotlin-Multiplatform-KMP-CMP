package com.touhidapps.room.utils

import kotlin.math.pow
import kotlin.math.round


fun Double.roundToFourDecimals(): Double {
    val factor = 10.0.pow(4)
    return round(this * factor) / factor
}