package com.msah.mobilepos.basket.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.airbnb.lottie.parser.IntegerParser
import com.google.firebase.auth.FirebaseAuth
import com.msah.mobilepos.R
import com.msah.mobilepos.basket.MpesaStkPush
import com.msah.mobilepos.data.model.DataState
import com.msah.mobilepos.data.model.Order
import com.msah.mobilepos.data.model.ProductBasket
import com.msah.mobilepos.data.preference.UserPref
import com.msah.mobilepos.data.repository.basket.BasketRepository
import com.msah.mobilepos.data.repository.user.UserRepositoryImpl
import kotlinx.coroutines.launch
import java.util.UUID

class BasketViewModel(private val basketRepository: BasketRepository) : ViewModel() {

    private var _basketCountLivedata = MutableLiveData<Int>()
    private var _basketTotalLiveData = MutableLiveData<Int>()

    val basketCountLiveData: LiveData<Int>
        get() = _basketCountLivedata
    val basketTotalLiveData: LiveData<Int>
        get() = _basketTotalLiveData


    var basketList = mutableListOf<ProductBasket>()
    private var _basketLiveData = MutableLiveData<DataState<List<ProductBasket>?>>()
    val basketLiveData: LiveData<DataState<List<ProductBasket>?>>
        get() = _basketLiveData


    private var _updateProductPieceLiveData = MutableLiveData<DataState<Int>>()
    val updateProductPieceLiveData: LiveData<DataState<Int>>
        get() = _updateProductPieceLiveData


    private var _purchaseLiveData = MutableLiveData<DataState<Int>>()
    val purchaseLiveData: LiveData<DataState<Int>>
        get() = _purchaseLiveData


    init {
        _basketTotalLiveData.value = 0
        _basketCountLivedata.value = 0
        getProductsBasket()
    }

    private fun getProductsBasket(){

        basketRepository.getAllProductsBasket()
            .addSnapshotListener{ value, error ->

                if(error == null){

                    basketList = mutableListOf()
                    var total = 0.0

                    value?.forEach {
                        val product = it.toObject(ProductBasket::class.java)
                        total += product.price!! * product.piece!!
                        basketList.add(product)
                    }

                    _basketLiveData.value = DataState.Success(basketList)
                    _basketTotalLiveData.value = total.toInt()
                    if (value != null) {
                        _basketCountLivedata.value = value.count()
                    }

                }else{
                    _basketLiveData.value = DataState.Error(error.message!!)
                }

            }

        }


    fun increaseProduct(productBasket: ProductBasket){

        if(productBasket.piece!! < 100){

            productBasket.piece = productBasket.piece!! + 1
            updateProductPiece(productBasket, true)

        }

    }


    fun reduceProduct(productBasket: ProductBasket){

        if(productBasket.piece!! > 1){

            productBasket.piece = productBasket.piece!! - 1
            updateProductPiece(productBasket, false)

        }else{
            deleteProduct(productBasket)
        }

    }

    private fun updateProductPiece(productBasket: ProductBasket, isIncrease: Boolean){

        basketRepository.updateProductsPiece(productBasket)
            .addOnSuccessListener {

                if(isIncrease) _updateProductPieceLiveData.value = DataState.Success(R.string.product_increased_message)
                else _updateProductPieceLiveData.value = DataState.Success(R.string.product_reduce_message)

            }
            .addOnFailureListener { e ->
                _updateProductPieceLiveData.value = DataState.Error(e.message!!)
            }

    }







    private fun deleteProduct(productBasket: ProductBasket){

        basketRepository.deleteProducts(productBasket)
            .addOnSuccessListener {
                _updateProductPieceLiveData.value = DataState.Success(R.string.product_deleted_message)
            }
            .addOnFailureListener { e ->
                _updateProductPieceLiveData.value = DataState.Error(e.message!!)
            }

    }



    fun clearTheBasket(){
        val orderId = UUID.randomUUID().toString()
        val uid =  FirebaseAuth.getInstance().currentUser!!.uid

        val  status = "PENDING"
        val processedBy =""
        val orderItems : MutableList<String> = mutableListOf()

        basketList.forEach {
            it.id?.let { it1 -> orderItems.add(it1) }
            deleteProduct(it)

        }
        val order = Order(uid, orderItems, processedBy, status)
        addProductToOrder(order, orderId)

        _purchaseLiveData.value = DataState.Success(R.string.purchase_success_message)

    }

    private fun addProductToOrder(order: Order, orderId: String){

        basketRepository.addProductToOrder(order, orderId)

    }

    fun makePayment(context:Context){
        val mpesaStkPush = MpesaStkPush()
        val userPref = UserPref(context)

        viewModelScope.launch {
            val paymentNo = userPref.getPhone()
            mpesaStkPush.sendRequest(basketTotalLiveData.value, paymentNo.toLong())

        }



    }

}