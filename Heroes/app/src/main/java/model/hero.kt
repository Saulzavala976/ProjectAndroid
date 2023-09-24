package model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class hero(
    @StringRes val nameRes: Int,
    @StringRes val descriptionRes: Int,
    @DrawableRes val imageRes: Int
)
