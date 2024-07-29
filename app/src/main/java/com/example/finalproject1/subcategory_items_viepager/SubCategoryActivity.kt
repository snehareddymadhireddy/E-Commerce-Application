package com.example.finalproject1.subcategory_items_viepager

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.finalproject1.databinding.ActivitySubCategoryBinding
import com.example.finalproject1.remote.APIClient
import com.example.finalproject1.remote.APIService
import com.example.finalproject1.remotedata.SubCategoryResponse
import com.example.finalproject1.data.Subcategory
import com.google.android.material.tabs.TabLayoutMediator
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SubCategoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySubCategoryBinding
    private val apiService: APIService = APIClient.retrofit.create(APIService::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySubCategoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val categoryId = intent.getStringExtra("CATEGORY_ID")
        if (categoryId != null) {
            getSubCategoryData(categoryId)
        } else {
            Toast.makeText(this, "Category ID is missing", Toast.LENGTH_SHORT).show()
        }
    }

    private fun getSubCategoryData(categoryId: String) {
        val call: Call<SubCategoryResponse> = apiService.getSubCategory(categoryId)
        call.enqueue(object : Callback<SubCategoryResponse> {
            override fun onResponse(call: Call<SubCategoryResponse>, response: Response<SubCategoryResponse>) {
                if (response.isSuccessful && response.body()?.status == 0) {
                    setupViewPager(response.body()?.subcategories ?: emptyList())
                }
                else {
                    Toast.makeText(this@SubCategoryActivity, "No subcategories found", Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<SubCategoryResponse>, t: Throwable) {
                Toast.makeText(this@SubCategoryActivity, "Failed to load subcategories", Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun setupViewPager(subcategories: List<Subcategory>) {
        val adapter = SubCategoryPagerAdapter(this, subcategories)
        binding.viewPager.adapter = adapter

        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = subcategories[position].subcategory_name
        }.attach()
    }
}
