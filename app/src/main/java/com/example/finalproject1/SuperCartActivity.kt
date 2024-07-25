package com.example.finalproject1

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.example.finalproject1.databinding.ActivitySuperCartBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.create
import java.util.Locale.Category

class SuperCartActivity : AppCompatActivity() {
    val apiService:APIService= APIClient.retrofit.create(APIService::class.java)
    private lateinit var binding: ActivitySuperCartBinding
    private lateinit var superCartAdapter: SuperCartAdapter
    private var cartItems= mutableListOf<CategoryData>()

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
                val intent = Intent(this@SuperCartActivity, SubCategoryActivity::class.java)
                intent.putExtra(Utils.CATEGORY_ID,it.category_id)
                startActivity(intent)
            }
            recyclerView.adapter = superCartAdapter
            btnLogout.setOnClickListener {
                SecuredSharedPreferenceManager.clearAllPref()
                showLogoutDialog()

            }

        }

        val call: Call<GetCategoryResponse> = apiService.getCategory()

        call.enqueue(object: Callback<GetCategoryResponse>
        {
            override fun onResponse(call: Call<GetCategoryResponse>, response: Response<GetCategoryResponse>) {
                if (response.isSuccessful) {
                    val serverResponse: GetCategoryResponse? = response.body()
                    if (serverResponse != null && serverResponse.status == 0) {
                        updateCategories(serverResponse.categories)
                    } else {
                        Toast.makeText(this@SuperCartActivity, serverResponse?.message ?: "Failed to fetch categories", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this@SuperCartActivity, "Failed to fetch categories. Error code: ${response.code()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(p0: Call<GetCategoryResponse>, p1: Throwable) {
                Toast.makeText(this@SuperCartActivity,"Unable to load cartItems. Please retry",Toast.LENGTH_LONG).show()
            }

        })

    }
    private fun updateCategories(categories:List<CategoryData>)
    {
        cartItems.clear()
        cartItems.addAll(categories.map { category ->
            CategoryData(
                category_name = category.category_name,
                category_image_url = Utils.BASE_IMAGE_URL + category.category_image_url,
                category_id = category.category_id,
                is_active = category.is_active
            )
        })



        superCartAdapter.notifyDataSetChanged()
    }

    private fun showLogoutDialog() {
        val builder = AlertDialog.Builder(this)
        builder.apply {
            setTitle("Logout")
            setMessage("Are you sure want to logout?")
            setPositiveButton("Okay") { _, _ ->
                finish()
            }
            setNegativeButton("No") { dialog, _ ->
                dialog.dismiss()
            }
        }

        val alertDialog:AlertDialog = builder.create()
        alertDialog.setCancelable(false)
        alertDialog.show()
    }

}
