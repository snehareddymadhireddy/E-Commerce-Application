package com.example.finalproject1.subcategory_items_viepager

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.finalproject1.data.Subcategory

class SubCategoryPagerAdapter(
    fa: FragmentActivity,
    private val subcategories: List<Subcategory>
) : FragmentStateAdapter(fa) {

    override fun getItemCount() = subcategories.size

    override fun createFragment(position: Int): Fragment {
        val subcategory = subcategories[position]
        return ProductListFragment.newInstance(subcategory.subcategory_id)
    }
}
