package com.example.finalproject1.local

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.finalproject1.data.Product

@Dao
interface ProductDao {

    @Query("SELECT * FROM cartItems")
    fun getAllCartItems(): LiveData<List<Product>>

    @Query("SELECT * FROM cartItems WHERE product_id = :productId LIMIT 1")
    fun getCartItemById(productId: String): Product?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCartItem(product: Product)

    @Update
    fun updateCartItem(product: Product)

    @Delete
    fun deleteCartItem(product: Product)
}
