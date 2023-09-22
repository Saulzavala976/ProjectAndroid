package com.example.appcuadrucula.affirmation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

class Topic (
    @StringRes val stringResourceId: Int,
    val number: Int,
    @DrawableRes val imageResourceId: Int
)