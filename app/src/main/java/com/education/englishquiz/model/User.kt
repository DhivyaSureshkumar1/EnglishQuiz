package com.education.englishquiz.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    val name: String,
    val grade: Int
):Parcelable

