// CheckoutSharedViewModel.kt
package com.example.finalproject1.svm

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.finalproject1.data.Product
import com.example.finalproject1.local.AppDatabase

class CheckoutSharedViewModel(application: Application) : AndroidViewModel(application) {

    private val productDao = AppDatabase.getInstance(application).productDao()

    val cartItems: LiveData<List<Product>> = productDao.getAllCartItems()

    private val _selectedPaymentMethod = MutableLiveData<String>()
    val selectedPaymentMethod: LiveData<String> get() = _selectedPaymentMethod

    private val _selectedAddress = MutableLiveData<String>()
    val selectedAddress: LiveData<String> get() = _selectedAddress

    fun setSelectedPaymentMethod(method: String) {
        Log.d("CheckoutSharedViewModel", "Payment method set to: $method")
        _selectedPaymentMethod.value = method
    }

    fun setSelectedAddress(address: String) {
        _selectedAddress.value = address
    }
}
