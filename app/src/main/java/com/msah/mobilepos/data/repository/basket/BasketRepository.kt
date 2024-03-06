package com.msah.mobilepos.data.repository.basket

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.msah.mobilepos.data.model.Order
import com.msah.mobilepos.data.model.ProductBasket

interface BasketRepository {

    fun getAllProductsBasket(): CollectionReference

    fun getTargetProductsBasket(productBasket: ProductBasket): DocumentReference

    fun addProductsToBasket(productBasket: ProductBasket): Task<Void>


    fun deleteProducts(productBasket: ProductBasket): Task<Void>

    fun addProductToOrder(order: Order, orderId: String): Task<DocumentReference>


    fun updateProductsPiece(productBasket: ProductBasket): Task<Void>


}