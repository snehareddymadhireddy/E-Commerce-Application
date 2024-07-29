package com.example.finalproject1.checkout_items_Viewpager

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.finalproject1.Constants
import com.example.finalproject1.data.Address
import com.example.finalproject1.data.AddressResponse
import com.example.finalproject1.databinding.DialogAddAddressBinding
import com.example.finalproject1.databinding.FragmentAddressViewPagerBinding
import com.example.finalproject1.remote.APIClient
import com.example.finalproject1.remote.APIService
import com.example.finalproject1.remotedata.AddAddressResponse
import com.example.finalproject1.remotedata.AddAddressRequest
import com.example.finalproject1.svm.CheckoutSharedViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddressViewPagerFragment : Fragment() {

    private lateinit var binding: FragmentAddressViewPagerBinding
    private lateinit var sharedViewModel: CheckoutSharedViewModel
    private lateinit var addressAdapter: AddressAdapter
    private val apiService: APIService = APIClient.retrofit.create(APIService::class.java)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddressViewPagerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedViewModel = ViewModelProvider(requireActivity()).get(CheckoutSharedViewModel::class.java)

        addressAdapter = AddressAdapter(emptyList()) { selectedAddress ->
            sharedViewModel.setSelectedAddress(selectedAddress.address)
        }
        binding.addressRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.addressRecyclerView.adapter = addressAdapter

        fetchAddresses()

        binding.btnAddAddress.setOnClickListener {

            showAddAddressDialog(Constants.USER_ID)
        }
    }

    private fun fetchAddresses() {
        val call: Call<AddressResponse> = apiService.getUserAddress()
        call.enqueue(object : Callback<AddressResponse> {
            override fun onResponse(call: Call<AddressResponse>, response: Response<AddressResponse>) {
                if (response.isSuccessful && response.body()?.status == 0) {
                    updateAddresses(response.body()?.addresses ?: emptyList())
                } else {
                    Toast.makeText(requireContext(), "Unable to fetch address", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<AddressResponse>, t: Throwable) {
                Toast.makeText(requireContext(), "Unable to fetch address", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun updateAddresses(addresses: List<Address>) {
        addressAdapter.updateAddresses(addresses)
    }

    private fun showAddAddressDialog(userId: String) {
        val dialogBinding = DialogAddAddressBinding.inflate(LayoutInflater.from(requireContext()))
        val dialog = AlertDialog.Builder(requireContext())
            .setView(dialogBinding.root)
            .create()

        dialogBinding.btnCancel.setOnClickListener { dialog.dismiss() }

        dialogBinding.btnSave.setOnClickListener {
            val title = dialogBinding.etAddressTitle.text.toString().trim()
            val address = dialogBinding.etAddress.text.toString().trim()

            if (title.isEmpty() || address.isEmpty()) {
                Toast.makeText(requireContext(), "Please fill in all fields", Toast.LENGTH_SHORT).show()
            } else {
                val addressRequest = AddAddressRequest(address = address,title=title,user_id=userId.toInt())
                addAddress(addressRequest)
                dialog.dismiss()
            }
        }

        dialog.show()
    }

    private fun addAddress(addressRequest: AddAddressRequest) {
        val call = apiService.addAddress(addressRequest)
        call.enqueue(object : Callback<AddAddressResponse> {
            override fun onResponse(call: Call<AddAddressResponse>, response: Response<AddAddressResponse>) {
                if (response.isSuccessful && response.body()?.status == 0) {
                    Toast.makeText(requireContext(), "Address added successfully", Toast.LENGTH_SHORT).show()

                    fetchAddresses()
                } else {
                    Toast.makeText(requireContext(), "Failed to add address", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<AddAddressResponse>, t: Throwable) {
                Toast.makeText(requireContext(), "Failed to add address", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
