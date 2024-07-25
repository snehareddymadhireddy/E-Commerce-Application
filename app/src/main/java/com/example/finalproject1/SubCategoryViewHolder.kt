package com.example.finalproject1

import androidx.recyclerview.widget.RecyclerView
import com.example.finalproject1.databinding.SubcategoryItemBinding
import com.squareup.picasso.Picasso

class SubCategoryViewHolder(private var binding:SubcategoryItemBinding, private val onItemClicked: (Subcategory)->Unit):RecyclerView.ViewHolder(binding.root) {
    fun bindData(category:Subcategory)
    {
        var url=Utils.BASE_IMAGE_URL+category.subcategory_image_url
        binding.tvSubcategoryItem.text=category.subcategory_name
        println("Loading image URL: ${category.subcategory_image_url}")
        Picasso.get().load(url).placeholder(R.drawable.baseline_logout_24).error(R.drawable.img_1).into(binding.ivSubcategoryItem)
        binding.root.setOnClickListener{
            onItemClicked(category)
        }
    }
}