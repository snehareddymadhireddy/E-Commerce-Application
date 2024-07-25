package com.example.finalproject1

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.AdapterView.OnItemClickListener
import androidx.recyclerview.widget.RecyclerView
import com.example.finalproject1.databinding.SubcategoryItemBinding

class SubCategoryAdapter(private val subcategory: List<Subcategory>, val onItemClicked:(Subcategory)->Unit):RecyclerView.Adapter<SubCategoryViewHolder>() {
    private lateinit var binding: SubcategoryItemBinding
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubCategoryViewHolder {
        binding= SubcategoryItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return SubCategoryViewHolder(binding,onItemClicked)
    }

    override fun getItemCount()= subcategory.size


    override fun onBindViewHolder(holder: SubCategoryViewHolder, position: Int) {
        holder.bindData(subcategory[position])
    }
}