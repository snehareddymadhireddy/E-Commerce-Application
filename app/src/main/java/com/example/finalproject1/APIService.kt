package com.example.finalproject1

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Query

interface APIService {
    @Headers("Content-type: application/json")
    @POST("User/register")
    fun addUser(
        @Body addUserRequest:AddUserRequest
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

}
