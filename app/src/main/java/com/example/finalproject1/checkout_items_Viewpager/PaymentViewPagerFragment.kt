package com.example.finalproject1.checkout_items_Viewpager

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.finalproject1.databinding.FragmentPaymentViewPagerBinding
import com.example.finalproject1.svm.CheckoutSharedViewModel

class PaymentViewPagerFragment : Fragment() {

    private lateinit var binding: FragmentPaymentViewPagerBinding
    private lateinit var sharedViewModel: CheckoutSharedViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPaymentViewPagerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedViewModel = ViewModelProvider(requireActivity()).get(CheckoutSharedViewModel::class.java)

        binding.radioGroupPaymentOptions.setOnCheckedChangeListener { group, checkedId ->
            val radioButton = group.findViewById<RadioButton>(checkedId)
            val selectedPaymentMethod = radioButton.text.toString()
            sharedViewModel.setSelectedPaymentMethod(selectedPaymentMethod)
        }

        binding.btnNext.setOnClickListener {

//             findNavController().navigate(R.id.action_paymentViewPagerFragment_to_nextFragment)
        }
    }
}
