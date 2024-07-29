// CartAdapter.kt
package com.example.finalproject1

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.finalproject1.data.Product
import com.example.finalproject1.databinding.CartListBinding
import com.example.finalproject1.local.AppDatabase
import com.squareup.picasso.Picasso

class CartAdapter(
    private val products: MutableList<Product>,
    private val cartDatabase: AppDatabase,
    private val onQuantityChanged: (Product) -> Unit
) : RecyclerView.Adapter<CartAdapter.ViewHolder>() {
    inner class ViewHolder(private val binding: CartListBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(product: Product) {
            with(binding) {
                Picasso.get().load("https://apolisrises.co.in/myshop/images/${product.product_image_url}")
                    .placeholder(R.drawable.baseline_downloading_24)
                    .error(R.drawable.baseline_downloading_24)
                    .into(imgProduct)
                txtProductName.text = product.product_name
                txtProductDescription.text = product.description
                txtProductPrice.text = "$${product.price}"
                etQuantity.setText(product.quantity.toString())
                txtTotalPrice.text = "$${product.quantity * product.price.toDouble()}"

                btnMinus.setOnClickListener {
                    val newQuantity = product.quantity - 1
                    if (newQuantity > 0) {
                        val updatedProduct = product.copy(quantity = newQuantity)
                        onQuantityChanged(updatedProduct)
                    }
                    else if(newQuantity==0){
//                        txtTotalPrice.text= (txtTotalPrice.text.toString().toDouble()-product.price.toDouble()).toString()
                        cartDatabase.productDao().deleteCartItem(product)

                        products.removeAt(adapterPosition)
                        notifyItemRemoved(adapterPosition)

                    }
                }

                btnPlus.setOnClickListener {
                    val newQuantity = product.quantity + 1
                    val updatedProduct = product.copy(quantity = newQuantity)
                    onQuantityChanged(updatedProduct)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = CartListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(products[position])
    }

    override fun getItemCount() = products.size
}
