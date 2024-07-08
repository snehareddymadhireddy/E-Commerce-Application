package com.example.finalproject1

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.finalproject1.databinding.CartListBinding
import com.example.finalproject1.databinding.SuperCartListBinding

class SuperCartAdapter(private val cartItems: List<CartData>,private val onItemClicked: () -> Unit) : RecyclerView.Adapter<SuperCartAdapter.CartViewHolder>() {
    private lateinit var binding:SuperCartListBinding

    inner class CartViewHolder(private var binding: SuperCartListBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(cartItem: CartData) {
            with(binding) {
                imageView.setImageResource(cartItem.img)
                textView.text = cartItem.item
                root.setOnClickListener { onItemClicked() }
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
