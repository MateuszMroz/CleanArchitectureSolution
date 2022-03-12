package com.example.cleanarchitecturesolution.core.data.remote.model

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class ResponseInfo(
    @SerializedName("count") val count: Int,
    @SerializedName("next") val next: String,
    @SerializedName("pages") val pages: Int,
    @SerializedName("prev") val prev: String,
) {
    companion object
}
