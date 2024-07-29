package com.example.finalproject1.checkout_items_Viewpager

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.finalproject1.R
import com.example.finalproject1.data.Address
import com.example.finalproject1.databinding.AddressItemBinding

class AddressAdapter(
    private var addresses: List<Address>,
    private val onAddressSelected: (Address) -> Unit
) : RecyclerView.Adapter<AddressAdapter.AddressViewHolder>() {

    private var selectedPosition: Int = -1

    inner class AddressViewHolder(private val binding: AddressItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("ResourceAsColor")
        fun bind(address: Address, isSelected: Boolean) {
            binding.tvAddressTitle.text = address.title
            binding.tvAddressDetail.text = address.address

            if (isSelected) {
                binding.root.background.setTint(R.color.Lblue)
            } else {
                binding.root.setBackgroundColor(Color.LTGRAY)
            }

            binding.root.setOnClickListener {
                val previousPosition = selectedPosition
                selectedPosition = adapterPosition
                notifyItemChanged(previousPosition)
                notifyItemChanged(selectedPosition)
                onAddressSelected(address)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddressViewHolder {
        val binding = AddressItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AddressViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AddressViewHolder, position: Int) {
        holder.bind(addresses[position], position == selectedPosition)
    }

    override fun getItemCount() = addresses.size

    fun updateAddresses(newAddresses: List<Address>) {
        addresses = newAddresses
        selectedPosition = -1
        notifyDataSetChanged()
    }
}
