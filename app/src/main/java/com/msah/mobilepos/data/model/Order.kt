package com.msah.mobilepos.data.model

class Order(
    var userId: String = "",
    var items: MutableList<String> = mutableListOf(),
    var processedBy: String = "",
    var status: String = "",
)
