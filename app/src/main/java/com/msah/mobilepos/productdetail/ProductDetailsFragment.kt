package com.msah.mobilepos.productdetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.snackbar.Snackbar
import com.msah.mobilepos.R
import com.msah.mobilepos.data.model.DataState
import com.msah.mobilepos.data.model.Product
import com.msah.mobilepos.data.repository.basket.BasketRepositoryImpl
import com.msah.mobilepos.databinding.FragmentProductDetailsBinding
import com.msah.mobilepos.loadingprogress.LoadingProgressBar
import com.msah.mobilepos.productdetail.viewmodel.ProductDetailViewModel
import com.msah.mobilepos.productdetail.viewmodel.ProductDetailViewModelFactory
import com.msah.mobilepos.utils.Constants.PRODUCT_MODEL_NAME

class ProductDetailsFragment : BottomSheetDialogFragment() {

    private lateinit var bnd: FragmentProductDetailsBinding
    private lateinit var loadingProgressBar: LoadingProgressBar
    private val viewModel by viewModels<ProductDetailViewModel> {
        ProductDetailViewModelFactory(
            BasketRepositoryImpl()
        )
    }




    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        bnd = DataBindingUtil.inflate(inflater, R.layout.fragment_product_details, container, false)
        return bnd.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init(){

        getBundleArguments()
        bnd.viewModel = viewModel
        bnd.productDetailsFragment = this

        loadingProgressBar = LoadingProgressBar(requireContext())

        viewModel.addBasketLiveData.observe(viewLifecycleOwner){

            when (it) {
                is DataState.Success -> {
                    loadingProgressBar.hide()
                    Toast.makeText(context, getString(R.string.product_added_basket_message), Toast.LENGTH_SHORT).show()
                }
                is DataState.Error -> {
                    loadingProgressBar.hide()
                    Snackbar.make(bnd.root, it.message, Snackbar.LENGTH_LONG).show()
                }
                is DataState.Loading -> {
                    loadingProgressBar.show()
                }

                else -> {}
            }

        }

    }




    private fun getBundleArguments(){

        arguments?.let {

            val productData = it.getString(PRODUCT_MODEL_NAME)

            // null state check of data
            productData?.let { safeData ->

                val product = Product.fromJson(safeData)
                bnd.dataHolder = product

                viewModel.productCountLiveData.observe(viewLifecycleOwner){ value ->
                    bnd.basketCount = value
                }

            }
        }

    }

    fun goBack(){
        findNavController().popBackStack()
    }

}