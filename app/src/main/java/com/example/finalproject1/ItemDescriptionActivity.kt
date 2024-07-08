package com.example.finalproject1

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.finalproject1.databinding.ActivityItemDescriptionBinding

class ItemDescriptionActivity : AppCompatActivity() {
    private lateinit var binding: ActivityItemDescriptionBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityItemDescriptionBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val item=intent.getParcelableExtra<Item>(Utils.ITEM)
        item?.let{
            binding.itemName.text=it.name
            binding.itemDescription.text=it.description
            binding.itemPrice.text=it.price.toString()
            binding.itemImage.setImageResource(it.image)

        }

    }
}