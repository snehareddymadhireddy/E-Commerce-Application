package com.example.finalproject1.checkout_items_Viewpager

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.finalproject1.databinding.ActivityCheckoutBinding
import com.example.finalproject1.svm.CheckoutSharedViewModel
import com.google.android.material.tabs.TabLayoutMediator

class CheckoutActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCheckoutBinding
    private val viewModel: CheckoutSharedViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCheckoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViewPager()
    }

    private fun setupViewPager() {
        val fragments = listOf(
            CartViewPagerFragment(),
            AddressViewPagerFragment(),
            PaymentViewPagerFragment(),
            SummaryViewPagerFragment()
        )

        val titles = listOf("Cart Items", "Delivery", "Payment", "Summary")

        val adapter = CheckoutPagerAdapter(this, fragments)
        binding.viewPager.adapter = adapter

        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = titles[position]
        }.attach()
    }
}
