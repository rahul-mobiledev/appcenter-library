package com.rahul.library.network

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Response(
    val name: String,
    val code: Int,
    val apkUrl: String,
    val type: UpdateType
) : Parcelable

enum class UpdateType {
    @SerializedName("IMMEDIATE")
    IMMEDIATE,

    @SerializedName("FLEXIBLE")
    FLEXIBLE;
}
