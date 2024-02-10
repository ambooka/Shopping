package com.msah.mobilepos.main.product.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.msah.mobilepos.data.model.DataState
import com.msah.mobilepos.data.model.Product
import com.msah.mobilepos.data.model.Rating

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProductViewModel() : ViewModel() {

    private var _productLiveData = MutableLiveData<List<Product>?>()
    val productLiveData: LiveData<List<Product>?>
        get() = _productLiveData

    init {
        getProducts()
    }

    private fun getProducts(){




        _productLiveData.postValue(generateProductList())



    }


    fun generateProductList(): List<Product> {
        return listOf(
            Product(
                category = "Electronics",
                description = "A powerful and versatile laptop for everyday use.",
                id = 1,
                image = "https://fakestoreapi.com/img/71kWymZ+c+L._AC_SX679_.jpg",
                price = 799.99,
                rating = Rating(150, 4.5),
                title = "Laptop X10"
            ),
            Product(
                category = "men's clothing",
                description = "A captivating novel about love, loss, and redemption.",
                id = 2,
                image = "https://example.com/book.jpg",
                price = 15.99,
                rating = Rating(250, 4.8),
                title = "The Book of Lost Things"
            ),
            Product(
                category = "women's clothing",
                description = "A cozy throw blanket to keep you warm on chilly nights.",
                id = 3,
                image = "https://example.com/blanket.jpg",
                price = 39.99,
                rating = Rating(100, 4.2),
                title = "Soft Fleece Blanket"
            ),
            // Add more products as needed
        )
    }


}