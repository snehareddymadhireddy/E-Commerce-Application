package com.example.finalproject1

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.example.finalproject1.databinding.ActivitySuperCartBinding

class SuperCartActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySuperCartBinding
    private lateinit var superCartAdapter: SuperCartAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySuperCartBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        initViews()
    }

    private fun initViews() {
        with(binding) {
            recyclerView.layoutManager = GridLayoutManager(this@SuperCartActivity, 2)
            superCartAdapter = SuperCartAdapter(cartItems)
            {
                val intent = Intent(this@SuperCartActivity, CategoryItemsActivity::class.java)
                startActivity(intent)
            }
            recyclerView.adapter = superCartAdapter

        }
    }

    private val cartItems = mutableListOf(
        CartData(item = "watch", img = R.drawable.img),
        CartData(item = "Food", img = R.drawable.img),
        CartData(item = "shoes", img = R.drawable.img),
        CartData(item = "Books", img = R.drawable.img),
        CartData(item = "pen", img = R.drawable.img),
            CartData(item = "mobile", img = R.drawable.img),
            CartData(item = "pencil", img = R.drawable.img),
            CartData(item = "friut", img = R.drawable.img)
    )
}
