package com.example.finalproject1

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.finalproject1.data.Item
import com.example.finalproject1.databinding.CategoryItemListBinding

class CategoryAdapter(private val items: List<Item>, private val onClick: (Item) -> Unit) : RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: CategoryItemListBinding) : RecyclerView.ViewHolder(binding.root) {

        init {
            itemView.setOnClickListener {
                onClick(items[adapterPosition])
            }

            binding.btnAddToCart.setOnClickListener {
                binding.btnAddToCart.visibility = View.GONE
                binding.quantityLayout.visibility = View.VISIBLE
            }

            binding.btnIncrease.setOnClickListener {
                var quantity = binding.quantityText.text.toString().toInt()
                quantity++
                binding.quantityText.text = quantity.toString()
            }

            binding.btnDecrease.setOnClickListener {
                var quantity = binding.quantityText.text.toString().toInt()
                if (quantity > 1) {
                    quantity--
                    binding.quantityText.text = quantity.toString()
                }
            }
        }

        fun bind(item: Item) {
            with(binding) {
                itemName.text = item.name
                itemDescription.text = item.description
                itemPrice.text = "$${item.price}"
                itemImage.setImageResource(item.image)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = CategoryItemListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount() = items.size
}
