package com.example.finalproject1

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.finalproject1.databinding.ActivityCartBinding
import com.example.finalproject1.data.Product
import com.example.finalproject1.local.AppDatabase
import com.example.finalproject1.checkout_items_Viewpager.CheckoutActivity
import com.example.finalproject1.svm.CheckoutSharedViewModel

class CartActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCartBinding
    private lateinit var cartAdapter: CartAdapter
    private lateinit var database: AppDatabase
    private var cartItems = mutableListOf<Product>()
    private val sharedViewModel: CheckoutSharedViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        database = AppDatabase.getInstance(this)
        cartAdapter = CartAdapter(cartItems, cartDatabase = database) { updatedProduct ->
            database.productDao().updateCartItem(updatedProduct)
            val index = cartItems.indexOfFirst { it.product_id == updatedProduct.product_id }
            if (index != -1) {
                cartItems[index] = updatedProduct
                cartAdapter.notifyItemChanged(index)
                updateTotalAmount()
            } else {
                Toast.makeText(this, "Failed to update item", Toast.LENGTH_SHORT).show()
            }
        }

        with(binding.cartRecyclerView) {
            layoutManager = LinearLayoutManager(this@CartActivity)
            adapter = cartAdapter
        }

        // Observe cart items from the ViewModel
        sharedViewModel.cartItems.observe(this, Observer { items ->
            cartItems.clear()
            cartItems.addAll(items)
            cartAdapter.notifyDataSetChanged()
            updateTotalAmount()
        })

        moveToCheckout()
    }

    private fun moveToCheckout() {
        binding.checkoutButton.setOnClickListener {
            val intent = Intent(this, CheckoutActivity::class.java)
            startActivity(intent)
        }
    }

    private fun updateTotalAmount() {
        val totalAmount = cartItems.sumOf { it.quantity * it.price.toDouble() }
        binding.totalAmount.text = "Total Amount: $$totalAmount"
    }
}
