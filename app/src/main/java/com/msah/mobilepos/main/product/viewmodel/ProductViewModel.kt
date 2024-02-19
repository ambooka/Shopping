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

        _productLiveData.postValue(null)

    }


//    fun generateProductList(): List<Product> {
//        return listOf(
//            Product(
//                description = "A powerful and versatile laptop for everyday use.",
//                id = "5034624108328",
//                imgURL = "https://fakestoreapi.com/img/71kWymZ+c+L._AC_SX679_.jpg",
//                price = 799.00,
//                name = "Laptop X10"
//            ),
//            Product(
//                description = "A captivating novel about love, loss, and redemption.",
//                id = "5034724308328",
//                imgURL = "https://example.com/book.jpg",
//                price = 15.00,
//                name = "The Book of Lost Things"
//            ),
//            Product(
//                description = "A cozy throw blanket to keep you warm on chilly nights.",
//                id = "5034624308328",
//                imgURL = "https://example.com/blanket.jpg",
//                price = 39.00,
//                name = "Soft Fleece Blanket"
//            ),
//            // Add more products as needed
//        )
//    }


}