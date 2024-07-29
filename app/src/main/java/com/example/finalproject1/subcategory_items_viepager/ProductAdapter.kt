
package com.example.finalproject1.subcategory_items_viepager

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.finalproject1.data.Product
import com.example.finalproject1.databinding.ItemProductBinding
import com.example.finalproject1.local.AppDatabase
import com.squareup.picasso.Picasso

class ProductAdapter(
    private val context: Context,
    private var products: List<Product>,
    private val onProductClick: (Product) -> Unit
) : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    private val cartDatabase: AppDatabase = AppDatabase.getInstance(context)

    inner class ProductViewHolder(private val binding: ItemProductBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(product: Product) {
            with(binding) {
                Picasso.get().load("https://apolisrises.co.in/myshop/images/${product.product_image_url}")
                    .into(itemImage)
                itemName.text = product.product_name
                itemDescription.text = product.description
                itemPrice.text = "$${product.price}"

                val cartItem = cartDatabase.productDao().getCartItemById(product.product_id)
                if (cartItem != null) {
                    btnAddToCart.visibility = View.GONE
                    quantityLayout.visibility = View.VISIBLE
                    quantityText.text = cartItem.quantity.toString()
                } else {
                    btnAddToCart.visibility = View.VISIBLE
                    quantityLayout.visibility = View.GONE
                }

                btnAddToCart.setOnClickListener {
                    val currentCartItem = cartDatabase.productDao().getCartItemById(product.product_id)
                    if (currentCartItem != null) {
                        val newQuantity = currentCartItem.quantity + 1
                        currentCartItem.quantity = newQuantity
                        cartDatabase.productDao().updateCartItem(currentCartItem)
                    } else {
                        val cartItem = product.copy(quantity = 1)
                        cartDatabase.productDao().insertCartItem(cartItem)
                    }
                    notifyDataSetChanged()
                }

                btnIncrease.setOnClickListener {
                    val currentCartItem = cartDatabase.productDao().getCartItemById(product.product_id) ?: return@setOnClickListener
                    val newQuantity = currentCartItem.quantity + 1
                    currentCartItem.quantity = newQuantity
                    cartDatabase.productDao().updateCartItem(currentCartItem)
                    notifyDataSetChanged()
                }

                btnDecrease.setOnClickListener {
                    val currentCartItem = cartDatabase.productDao().getCartItemById(product.product_id) ?: return@setOnClickListener
                    if (currentCartItem.quantity > 1) {
                        val newQuantity = currentCartItem.quantity - 1
                        currentCartItem.quantity = newQuantity
                        cartDatabase.productDao().updateCartItem(currentCartItem)
                    } else {
                        cartDatabase.productDao().deleteCartItem(currentCartItem)
                        btnAddToCart.visibility = View.VISIBLE
                        quantityLayout.visibility = View.GONE
                    }
                    notifyDataSetChanged()
                }

                root.setOnClickListener {
                    onProductClick(product)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val binding = ItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bind(products[position])
    }

    override fun getItemCount(): Int = products.size

    fun updateList(newProducts: List<Product>) {
        products = newProducts
        notifyDataSetChanged()
    }
}
