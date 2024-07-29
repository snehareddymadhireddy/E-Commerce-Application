package com.example.finalproject1.remote

import com.example.finalproject1.Constants
import com.example.finalproject1.data.AddressResponse
import com.example.finalproject1.data.ProductResponse
import com.example.finalproject1.remotedata.AddAddressRequest
import com.example.finalproject1.remotedata.AddAddressResponse
import com.example.finalproject1.remotedata.AddUserLoginRequest
import com.example.finalproject1.remotedata.AddUserLoginResponse
import com.example.finalproject1.remotedata.AddUserRequest
import com.example.finalproject1.remotedata.AddUserResponse
import com.example.finalproject1.remotedata.GetCategoryResponse
import com.example.finalproject1.remotedata.SubCategoryResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface
APIService {
    @Headers("Content-type: application/json")
    @POST("User/register")
    fun addUser(
        @Body addUserRequest: AddUserRequest
    ): Call<AddUserResponse>
    @POST("User/auth")
    fun addUserLogin(
        @Body addUserLoginRequest: AddUserLoginRequest
    ):Call<AddUserLoginResponse>
    @GET("Category")
    fun getCategory():Call<GetCategoryResponse>
    @GET("SubCategory")
    fun getSubCategory(
        @Query("category_id") categoryId:String
    ):Call<SubCategoryResponse>
    @GET("Product/search")
    fun getAllItemsBySearch(
        @Query("search_text") searchText:String
    )
    @GET("User/addresses/{user_id}")
    fun getUserAddress(
        @Path("user_id") user_id:String =Constants.USER_ID
    ):Call<AddressResponse>
    @GET("SubCategory/products/{sub_category_id}")
    fun getProductsBySubCategory(@Path("sub_category_id") subCategoryId: String): Call<ProductResponse>

    @POST("User/address")
    fun addAddress(@Body addressRequest: AddAddressRequest): Call<AddAddressResponse>

}
