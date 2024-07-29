package com.example.finalproject1.checkout_items_Viewpager

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.finalproject1.Constants
import com.example.finalproject1.data.Product
import com.example.finalproject1.databinding.FragmentCartViewPagerBinding
import com.example.finalproject1.svm.CheckoutSharedViewModel

class CartViewPagerFragment : Fragment() {

    private lateinit var binding: FragmentCartViewPagerBinding
    private lateinit var sharedViewModel: CheckoutSharedViewModel
    private lateinit var cartAdapter: CartSummaryAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCartViewPagerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedViewModel = ViewModelProvider(requireActivity()).get(CheckoutSharedViewModel::class.java)

        cartAdapter = CartSummaryAdapter(sharedViewModel.cartItems.value ?: emptyList())

        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = cartAdapter
        Toast.makeText(requireContext(),"user id is ${Constants.USER_ID}", Toast.LENGTH_LONG).show()

        sharedViewModel.cartItems.observe(viewLifecycleOwner) { items ->
            cartAdapter.updateList(items)
            updateTotalBillAmount(items)
        }


    }

    private fun updateTotalBillAmount(items: List<Product>) {
        val totalBillAmount = items.sumOf { it.price.toDouble() * it.quantity }
        binding.totalBillAmount.text = "$totalBillAmount"
    }
}
