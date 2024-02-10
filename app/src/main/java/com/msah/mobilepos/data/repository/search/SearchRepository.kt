package com.msah.mobilepos.data.repository.search

import com.msah.mobilepos.data.model.Product
import retrofit2.Call

interface SearchRepository {

    fun getProducts(): Call<List<Product>>

    fun getProductsByCategory(category: String): Call<List<Product>>

    fun getCategories(): Call<List<String>>

}