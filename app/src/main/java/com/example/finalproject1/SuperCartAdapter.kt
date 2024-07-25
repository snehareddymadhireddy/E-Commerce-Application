package com.example.finalproject1

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.finalproject1.databinding.CartListBinding
import com.example.finalproject1.databinding.SuperCartListBinding
import com.squareup.picasso.Picasso

class SuperCartAdapter(private val cartItems: List<CategoryData>,private val onItemClicked: (CategoryData) -> Unit) : RecyclerView.Adapter<SuperCartAdapter.CartViewHolder>() {
    private lateinit var binding:SuperCartListBinding

    inner class CartViewHolder(private var binding: SuperCartListBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(cartItem: CategoryData) {
            with(binding) {
                println("Loading image URL: ${cartItem.category_image_url}")
                Picasso.get().load(cartItem.category_image_url).into(imageView);
//                imageView.setImageResource("https://apolisrises.co.in/myshop/images/${cartItem.category_image_url}")
                textView.text=cartItem.category_name

                root.setOnClickListener { onItemClicked(cartItem) }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        binding = SuperCartListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CartViewHolder(binding)
    }

    override fun getItemCount() = cartItems.size

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        holder.bind(cartItems[position])
    }
}
