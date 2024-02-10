package com.msah.mobilepos.main.product

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.msah.mobilepos.R
import com.msah.mobilepos.data.model.DataState
import com.msah.mobilepos.data.model.Product
import com.msah.mobilepos.data.model.Rating
import com.msah.mobilepos.loadingprogress.LoadingProgressBar
import com.msah.mobilepos.main.product.adapter.ProductAdapter
import com.msah.mobilepos.main.product.viewmodel.ProductViewModel
import com.msah.mobilepos.main.product.viewmodel.ProductViewModelFactory

class ProductFragment : Fragment() {

    private lateinit var bnd: com.msah.mobilepos.databinding.FragmentProductBinding
    private lateinit var productAdapter: ProductAdapter
    private lateinit var loadingProgressBar: LoadingProgressBar
    private val viewModel by viewModels<ProductViewModel> {
        ProductViewModelFactory(
        )
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        bnd = DataBindingUtil.inflate(inflater, R.layout.fragment_product, container, false)
        return bnd.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadingProgressBar = LoadingProgressBar(requireContext())

        viewModel.productLiveData.observe(viewLifecycleOwner){

            productAdapter = ProductAdapter(requireContext(), generateProductList(), parentFragmentManager)
            bnd.gridViewProduct.adapter = productAdapter
        }

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