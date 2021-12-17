package com.example.quizy.utils

import com.example.quizy.R

object IconPicker {

    val icon = arrayOf(R.drawable.ic_icon1, R.drawable.ic_icon2, R.drawable.ic_icon3)
    var currentIconIndex = 0

    fun getIcon(): Int{
        currentIconIndex = (currentIconIndex + 1) % icon.size
        return icon[currentIconIndex]
    }

}