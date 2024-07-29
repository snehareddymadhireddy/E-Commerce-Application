package com.example.finalproject1.checkout_items_Viewpager

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.finalproject1.databinding.FragmentSummaryViewPagerBinding
import com.example.finalproject1.svm.CheckoutSharedViewModel

class SummaryViewPagerFragment : Fragment() {

    private lateinit var binding: FragmentSummaryViewPagerBinding
    private lateinit var sharedViewModel: CheckoutSharedViewModel
    private lateinit var cartAdapter: CartSummaryAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSummaryViewPagerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedViewModel = ViewModelProvider(requireActivity()).get(CheckoutSharedViewModel::class.java)

        cartAdapter = CartSummaryAdapter(sharedViewModel.cartItems.value ?: emptyList())

        binding.rvCartItems.layoutManager = LinearLayoutManager(requireContext())
        binding.rvCartItems.adapter = cartAdapter

        sharedViewModel.cartItems.observe(viewLifecycleOwner, { items ->
            cartAdapter.updateList(items)
        })

        sharedViewModel.selectedPaymentMethod.observe(viewLifecycleOwner, { method ->
            Log.d("SummaryViewPagerFragment", "Observed payment method: $method")
            binding.tvPaymentOption.text = method
        })

        sharedViewModel.selectedAddress.observe(viewLifecycleOwner, { address ->
            binding.tvDeliveryAddress.text = address.toString()
        })
    }
}
