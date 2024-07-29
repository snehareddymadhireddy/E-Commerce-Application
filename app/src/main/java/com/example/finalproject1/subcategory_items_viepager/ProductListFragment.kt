
package com.example.finalproject1.subcategory_items_viepager

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.finalproject1.data.Product
import com.example.finalproject1.data.ProductResponse
import com.example.finalproject1.databinding.FragmentProductListBinding
import com.example.finalproject1.remote.APIClient
import com.example.finalproject1.remote.APIService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProductListFragment : Fragment() {
    private lateinit var binding: FragmentProductListBinding
    private lateinit var productAdapter: ProductAdapter
    private val apiService: APIService = APIClient.retrofit.create(APIService::class.java)

    companion object {
        private const val SUB_CATEGORY_ID = "SUB_CATEGORY_ID"

        fun newInstance(subCategoryId: String): ProductListFragment {
            val fragment = ProductListFragment()
            val args = Bundle().apply { putString(SUB_CATEGORY_ID, subCategoryId) }
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentProductListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        productAdapter = ProductAdapter(requireContext(), emptyList()) { product -> onProductClick(product) }
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = productAdapter

        val subCategoryId = arguments?.getString(SUB_CATEGORY_ID) ?: return
        getProducts(subCategoryId)
    }

    private fun getProducts(subCategoryId: String) {
        val call: Call<ProductResponse> = apiService.getProductsBySubCategory(subCategoryId)
        call.enqueue(object : Callback<ProductResponse> {
            override fun onResponse(call: Call<ProductResponse>, response: Response<ProductResponse>) {
                if (response.isSuccessful && response.body()?.status == 0) {
                    updateProductList(response.body()?.products ?: emptyList())
                } else {
                    Toast.makeText(requireContext(), "No products found", Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<ProductResponse>, t: Throwable) {
                Toast.makeText(requireContext(), "Failed to load products", Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun updateProductList(products: List<Product>) {
        productAdapter.updateList(products)
    }

    private fun onProductClick(product: Product) {

        Toast.makeText(requireContext(), "Clicked on ${product.product_name}", Toast.LENGTH_SHORT).show()
    }
}
