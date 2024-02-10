package com.msah.mobilepos.productdetail.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.msah.mobilepos.data.repository.basket.BasketRepository

class ProductDetailViewModelFactory(private val basketRepository: BasketRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ProductDetailViewModel(basketRepository) as T
    }
}