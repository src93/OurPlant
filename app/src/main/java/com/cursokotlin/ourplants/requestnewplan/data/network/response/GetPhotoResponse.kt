package com.cursokotlin.ourplants.requestnewplan.data.network.response

import com.google.gson.annotations.SerializedName

data class GetPhotoResponse(
    @SerializedName("total") val total: Int,
    @SerializedName("total_pages") val totalPages: Int,
    @SerializedName("results") val results: List<ItemResult>
)

data class ItemResult(
    @SerializedName("urls") val urls: Urls
)

data class Urls(
    @SerializedName("full") val full: String,
    @SerializedName("regular") val regular: String,
    @SerializedName("small") val small: String
)