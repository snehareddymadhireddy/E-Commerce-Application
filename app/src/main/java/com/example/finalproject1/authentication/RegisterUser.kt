package com.example.finalproject1.authentication

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.finalproject1.categorypage.SuperCartActivity
import com.example.finalproject1.data.UserRegisterInfo
import com.example.finalproject1.databinding.ActivityRegisterUserBinding
import com.example.finalproject1.remote.APIClient
import com.example.finalproject1.remote.APIService
import com.example.finalproject1.remotedata.AddUserRequest
import com.example.finalproject1.remotedata.AddUserResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterUser : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterUserBinding
    val apiService: APIService = APIClient.retrofit.create(APIService::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterUserBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViews()
    }

    private fun initViews() {
        with(binding) {
            btnRegister.setOnClickListener {
                val name = etFullName.text.toString()
                val mobileNo = etMobileNo.text.toString()
                val emailId = etEmailId.text.toString()
                val password = etPassword.text.toString()
                addUser(UserRegisterInfo(email_id = emailId, full_name = name, mobile_no = mobileNo, password = password))
            }
        }
    }

    private fun saveTheUserInfo(name: String, mobileNo: String, email: String, password: String) {
        SecuredSharedPreferenceManager.saveString(SecuredSharedPreferenceManager.NAME_SECURED, name)
        SecuredSharedPreferenceManager.saveString(SecuredSharedPreferenceManager.MOBILE_NUMBER_SECURED, mobileNo)
        SecuredSharedPreferenceManager.saveString(SecuredSharedPreferenceManager.USER_EMAIL_SECURED, email)
        SecuredSharedPreferenceManager.saveString(SecuredSharedPreferenceManager.PASSWORD_SECURED, password)
        SecuredSharedPreferenceManager.saveBooleanAndGetStatus(SecuredSharedPreferenceManager.IS_LOGGED_IN_SECURED, true)

        moveToDashBoardScreen()
    }

    private fun addUser(user: UserRegisterInfo) {
        val request = AddUserRequest(user.email_id, user.full_name, user.mobile_no, user.password)
        val call: Call<AddUserResponse> = apiService.addUser(request)
        call.enqueue(object : Callback<AddUserResponse> {
            override fun onResponse(call: Call<AddUserResponse>, response: Response<AddUserResponse>) {
                val serverResponse: AddUserResponse? = response.body()
                if (serverResponse != null) {
                    if (response.isSuccessful && serverResponse.status == 0) {
                        Toast.makeText(this@RegisterUser, serverResponse.message ?: "Success", Toast.LENGTH_LONG).show()
                        saveTheUserInfo(user.full_name, user.mobile_no, user.email_id, user.password)
                    } else {
                        Toast.makeText(this@RegisterUser, "User already has an account, please login. Error code: ${response.code()}", Toast.LENGTH_LONG).show()
                    }
                }
            }

            override fun onFailure(call: Call<AddUserResponse>, t: Throwable) {
                t.printStackTrace()
                Toast.makeText(this@RegisterUser, "Unknown error. Please retry.", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun moveToDashBoardScreen() {
        startActivity(Intent(this, SuperCartActivity::class.java))
        finish()
    }
}
