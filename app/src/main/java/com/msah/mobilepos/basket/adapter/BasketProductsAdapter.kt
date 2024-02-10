package com.msah.mobilepos.basket.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.msah.mobilepos.data.model.ProductBasket
import com.msah.mobilepos.databinding.ItemProductBasketBinding
import com.msah.mobilepos.basket.ProductPieceUpdateListener

class BasketProductsAdapter(
    private val basketProductsList: List<ProductBasket>,
    val productPieceUpdateListener: ProductPieceUpdateListener
) : RecyclerView.Adapter<BasketProductsAdapter.BasketProductsViewHolder>() {

    private lateinit var binding: ItemProductBasketBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BasketProductsViewHolder {
        binding = ItemProductBasketBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BasketProductsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BasketProductsViewHolder, position: Int) {
        val product = basketProductsList[position]
        holder.bind(product)
    }
    
    inner class BasketProductsViewHolder(private val binding: ItemProductBasketBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(basketProduct: ProductBasket) {
            binding.dataHolder = basketProduct
            binding.productAdapter = this@BasketProductsAdapter
            binding.executePendingBindings()
        }

    }

    override fun getItemCount(): Int = basketProductsList.size

}