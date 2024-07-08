package com.example.finalproject1

import CartAdapter
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.finalproject1.databinding.ActivityCartBinding

class CartActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCartBinding
    private lateinit var cartAdapter: CartAdapter
    private lateinit var databaseHelper: CartDatabaseHelper
    private var cartItems = mutableListOf<Cart>()
    private var productDataList = mutableListOf<ProductData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        productDataList = mutableListOf(
            ProductData(1, "FOSSIL", "Fossil Men's Grant Quartz Stainless Steel and Leather", 50.0, R.drawable.img_1),
            ProductData(2, "FIZILI", "Mens Watches Ultra-Thin Minimalist Waterproof ", 150.0, R.drawable.img_2),
            ProductData(3, "OLEVS", "Bring the party anywhere with this compact yet powerful Bluetooth speaker. Enjoy deep bass and clear highs for an immersive audio experience.", 30.0, R.drawable.img_3)
        )



        databaseHelper = CartDatabaseHelper(this)


        if (databaseHelper.getAllCartItems().isEmpty()) {
            productDataList.forEach { product ->
                databaseHelper.insertCartItem(Cart(product.id, 1, product.price, product.price))
            }
        }


        cartItems.addAll(databaseHelper.getAllCartItems())


        cartAdapter = CartAdapter(cartItems, productDataList) { cartItem ->
            val updated = databaseHelper.updateCartItem(cartItem)
            if (updated > 0) {
                val index = cartItems.indexOfFirst { it.productId == cartItem.productId }
                if (index != -1) {
                    cartItems[index] = cartItem
                    cartAdapter.notifyItemChanged(index)
                    updateTotalAmount()
                }
            } else {
                Toast.makeText(this, "Failed to update item", Toast.LENGTH_SHORT).show()
            }
        }


        with(binding.cartRecyclerView) {
            layoutManager = LinearLayoutManager(this@CartActivity)
            adapter = cartAdapter
        }


        updateTotalAmount()
    }


    private fun updateTotalAmount() {
        val totalAmount = cartItems.sumOf { it.totalPrice }
        binding.totalAmount.text = "Total Amount: $$totalAmount"
    }
}
