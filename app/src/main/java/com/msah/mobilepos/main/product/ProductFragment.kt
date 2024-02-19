package com.msah.mobilepos.main.product

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.FirebaseFirestore
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

            val db = FirebaseFirestore.getInstance()


            val productsCollection = db.collection("products")

            productsCollection.get().addOnSuccessListener { querySnapshot ->
                val productList = mutableListOf<Product>()

                for (document in querySnapshot.documents) {
                    val product = document.toObject(Product::class.java)
                    if (product != null) {
                    }
                    product?.let { productList.add(it) }
                }

                productAdapter = ProductAdapter(requireContext(), productList, parentFragmentManager)
                bnd.gridViewProduct.adapter = productAdapter

            }.addOnFailureListener { exception ->
                // Handle any errors
                Toast.makeText(context, "Error getting Products: ${exception.message}", Toast.LENGTH_SHORT).show()
            }


        }

    }


}