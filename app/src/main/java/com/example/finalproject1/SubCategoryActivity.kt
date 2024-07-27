package com.example.finalproject1

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.finalproject1.databinding.ActivitySubCategoryBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.create

class SubCategoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySubCategoryBinding
    private var subcategoryItemList = mutableListOf<Subcategory>()
    private lateinit var subcategoryAdapter: SubCategoryAdapter
    private val apiService: APIService = APIClient.retrofit.create(APIService::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySubCategoryBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)

        val categoryId = intent.getStringExtra(Utils.CATEGORY_ID)
        if (categoryId != null) {
            getSubCategoryData(categoryId)
        }
        else{
            Toast.makeText(this, "Category ID is missing", Toast.LENGTH_SHORT).show()
        }

        setupRecyclerView()
        binding.btnMovetoCart.setOnClickListener {
            val intent= Intent(this,CartActivity::class.java)
            startActivity(intent) }

    }

    private fun setupRecyclerView() {
        binding.recyclerViewSubCategory.layoutManager = LinearLayoutManager(this)
        subcategoryAdapter = SubCategoryAdapter(subcategoryItemList) { subcategory ->
            val intent = Intent(this, CategoryItemsActivity::class.java)
            intent.putExtra("SUBCATEGORY_ID", subcategory.subcategory_id)
            startActivity(intent)
        }
        binding.recyclerViewSubCategory.adapter = subcategoryAdapter
    }

    private fun getSubCategoryData(cid: String) {
        val call: Call<SubCategoryResponse> = apiService.getSubCategory(cid)
        call.enqueue(object : Callback<SubCategoryResponse> {
            override fun onResponse(call: Call<SubCategoryResponse>, response: Response<SubCategoryResponse>) {
                val searchResponse = response.body()
                if (searchResponse == null) {
                    Toast.makeText(this@SubCategoryActivity, "NO SUBCATEGORIES FOUND FOR THIS CATEGORY", Toast.LENGTH_LONG).show()
                    return
                }
                if (!response.isSuccessful) {
                    val error = response.errorBody()?.string() ?: "Unknown server error. Please retry."
                    Toast.makeText(this@SubCategoryActivity, error, Toast.LENGTH_LONG).show()
                    return
                }
                updateSubCategoryItems(searchResponse.subcategories)
            }

            override fun onFailure(call: Call<SubCategoryResponse>, t: Throwable) {
                Toast.makeText(this@SubCategoryActivity, "Unable to load subcategories. Please retry.", Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun updateSubCategoryItems(subcategories: List<Subcategory>) {
        subcategoryItemList.clear()
        subcategoryItemList.addAll(subcategories.map{sub->
            Subcategory(category_id =sub.category_id, is_active = sub.is_active, subcategory_id = sub.subcategory_id, subcategory_image_url = sub.subcategory_image_url, subcategory_name = sub.subcategory_name)
        })
        subcategoryAdapter.notifyDataSetChanged()
    }
}
