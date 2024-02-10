package com.msah.mobilepos.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Rating(
    @SerializedName("count")
    val count: Int?,
    @SerializedName("rate")
    val rate: Double?
) : Parcelable