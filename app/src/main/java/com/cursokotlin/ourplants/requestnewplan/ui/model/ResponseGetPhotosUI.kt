package com.cursokotlin.ourplants.requestnewplan.ui.model

data class ResponseGetPhotosUI(val photos: List<ItemResult>)

data class ItemResult(val urls: Urls)

data class Urls(val full: String, val regular: String, val small: String)
