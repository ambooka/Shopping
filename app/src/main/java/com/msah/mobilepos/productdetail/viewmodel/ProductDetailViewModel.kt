package com.msah.mobilepos.productdetail.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.ListenerRegistration
import com.msah.mobilepos.data.model.DataState
import com.msah.mobilepos.data.model.Product
import com.msah.mobilepos.data.model.ProductBasket
import com.msah.mobilepos.data.repository.basket.BasketRepository

class ProductDetailViewModel(private val basketRepository: BasketRepository) : ViewModel() {

    val productCountLiveData = MutableLiveData<Int>()

    private var _addBasketLiveData = MutableLiveData<DataState<Boolean>>()
    val addBasketLiveData: LiveData<DataState<Boolean>>
        get() = _addBasketLiveData

    init {
        setDefaultCount()
    }

    private fun setDefaultCount(){
        productCountLiveData.value = 1
    }

    fun reduceCount(value:Int){
        if(value > 1) productCountLiveData.value = value-1
    }

    fun increaseCount(value:Int){
        if(value < 100) productCountLiveData.value = value+1
    }

    fun checkProduct(product: Product){

        _addBasketLiveData.value = DataState.Loading()

        val productBasket = ProductBasket(
            product.id,
            product.name,
            product.imgUrl,
            product.price,
            productCountLiveData.value
        )

        var feedback: ListenerRegistration? = null

        feedback = basketRepository.getTargetProductsBasket(productBasket)
            .addSnapshotListener{ value, error ->

                if(error == null){

                    val firestoreProduct = value?.toObject(ProductBasket::class.java)

                    if(firestoreProduct == null){
                        addProductsToBasket(productBasket)
                    }else{
                        firestoreProduct.piece = (firestoreProduct.piece!! + productBasket.piece!!)
                        updateProduct(firestoreProduct)
                    }

                }else{
                    addProductsToBasket(productBasket)
                }

                feedback?.remove()

            }

    }

    private fun addProductsToBasket(productBasket: ProductBasket){

        basketRepository.addProductsToBasket(productBasket)
            .addOnSuccessListener {
                setDefaultCount()
                _addBasketLiveData.value = DataState.Success(true)
            }
            .addOnFailureListener { e ->
                _addBasketLiveData.value = DataState.Error(e.message!!)
            }

    }

    private fun updateProduct(productBasket: ProductBasket){

        basketRepository.updateProductsPiece(productBasket)
            .addOnSuccessListener {

                setDefaultCount()
                _addBasketLiveData.value = DataState.Success(true)

            }
            .addOnFailureListener { e ->
                _addBasketLiveData.value = DataState.Error(e.message!!)
            }

    }

}