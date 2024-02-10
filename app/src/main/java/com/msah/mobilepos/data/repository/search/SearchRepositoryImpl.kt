package com.msah.mobilepos.data.repository.search

import com.msah.mobilepos.data.model.Product
import com.msah.mobilepos.data.repository.search.SearchRepository
import retrofit2.Call

class SearchRepositoryImpl constructor() : SearchRepository {

    override fun getProducts(): Call<List<Product>> {
        return getProducts()
    }

    override fun getCategories(): Call<List<String>> {
        return getCategories()
    }

    override fun getProductsByCategory(category: String): Call<List<Product>> {
        return getProductsByCategory(category)
    }

}