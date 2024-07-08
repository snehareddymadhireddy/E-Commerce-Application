// CategoryItemsActivity.kt
package com.example.finalproject1

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.finalproject1.databinding.ActivityCategoryItemsBinding

class CategoryItemsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCategoryItemsBinding
    private lateinit var categoryAdapter: CategoryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCategoryItemsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val category = intent.getStringExtra("category") ?: ""

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        categoryAdapter = CategoryAdapter(getItemsForCategory(category)) { item ->
            val intent = Intent(this, ItemDescriptionActivity::class.java)
            intent.putExtra(Utils.ITEM, item)
            startActivity(intent)
        }
        binding.recyclerView.adapter = categoryAdapter
    }

    private fun getItemsForCategory(category: String): List<Item> {

        return listOf(
            Item("Watch 1", "Description 1", 100.0, R.drawable.img),
            Item("Watch 2", "Description 2", 200.0, R.drawable.img)

        )
    }
}
