package com.example.finalproject1.checkout_items_Viewpager

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.finalproject1.R
import com.example.finalproject1.data.Product
import com.example.finalproject1.databinding.ItemCartSummaryBinding
import com.squareup.picasso.Picasso

class CartSummaryAdapter(
    private var products: List<Product>
) : RecyclerView.Adapter<CartSummaryAdapter.CartViewHolder>() {

    inner class CartViewHolder(private val binding: ItemCartSummaryBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(product: Product) {
            with(binding) {
                Picasso.get().load("https://apolisrises.co.in/myshop/images/${product.product_image_url}")
                    .error(R.drawable.baseline_downloading_24)
                    .into(productImage)
                productName.text = product.product_name
                productUnitPrice.text = "$${product.price}"
                productQuantity.text = "Quantity: ${product.quantity}"
                productTotalAmount.text = "Amount: $${product.price.toDouble() * product.quantity}"
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val binding = ItemCartSummaryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CartViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        holder.bind(products[position])
    }

    override fun getItemCount(): Int = products.size

    fun updateList(newProducts: List<Product>) {
        products = newProducts
        notifyDataSetChanged()
    }

    fun calculateTotalAmount(): Double {
        return products.sumOf { it.price.toDouble() * it.quantity }
    }
}
