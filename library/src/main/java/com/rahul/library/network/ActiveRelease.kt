package com.rahul.library.network

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class UpdateResponse(
    val app: String,
    @SerializedName("active_release")
    val activeRelease: ActiveRelease?
) : Parcelable

@Parcelize
data class ActiveRelease(
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
