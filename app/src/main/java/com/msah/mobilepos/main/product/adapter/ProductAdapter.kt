package com.msah.mobilepos.main.product.adapter

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import androidx.navigation.NavController
import com.msah.mobilepos.R
import com.msah.mobilepos.data.model.Product
import com.msah.mobilepos.databinding.ItemProductBinding
import com.msah.mobilepos.productdetail.ProductDetailsFragment
import com.msah.mobilepos.utils.Constants.PRODUCT_MODEL_NAME

class ProductAdapter (context: Context, productList: List<Product?>, private val fragManager: FragmentManager) :
    ArrayAdapter<Product?>(context, 0, productList) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val binding: ItemProductBinding

        if (convertView == null || convertView.tag == null) {

            binding = ItemProductBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )

        }else{

            binding = convertView.tag as ItemProductBinding

        }

        val product: Product? = getItem(position)
        binding.dataHolder = product
        binding.llProductItem.setOnClickListener {

            goProductDetails(product)
        }

        return binding.root

    }

    private fun goProductDetails(product: Product?){


        val bundle = Bundle().apply {
            putString(PRODUCT_MODEL_NAME, product?.toJson()) // Assuming product is your data object
        }
        ProductDetailsFragment().run {
            arguments = bundle
            show(fragManager, "Details")
        }

    }

}