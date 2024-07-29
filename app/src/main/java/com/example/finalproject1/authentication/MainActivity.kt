package com.example.finalproject1.authentication

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.finalproject1.Constants
import com.example.finalproject1.categorypage.SuperCartActivity
import com.example.finalproject1.databinding.ActivityMainBinding
import com.example.finalproject1.remote.APIClient
import com.example.finalproject1.remote.APIService
import com.example.finalproject1.remotedata.AddUserLoginRequest
import com.example.finalproject1.remotedata.AddUserLoginResponse
import com.google.android.material.snackbar.Snackbar
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.regex.Pattern

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    val apiService: APIService = APIClient.retrofit.create(APIService::class.java)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViews()
    }

    private fun initViews() {
        checkIsUserAlreadyLoggedIn()

        with(binding) {
            register.setOnClickListener {
                // Intent to open RegisterUser activity
                val intent = Intent(this@MainActivity, RegisterUser::class.java)
                startActivity(intent)
                finish()
            }

            btnlogin.setOnClickListener {
                val email = emailTxt.text.toString()
                val password = passTxt.text.toString()

                if (validateInputs(email, password)) {
                    addUserLogin(email,password)

                }
            }
        }
    }

    private fun addUserLogin(email: String, password: String) {
        val request= AddUserLoginRequest(email_id = email, password = password)
        val call: Call<AddUserLoginResponse> = apiService.addUserLogin(request)
        call.enqueue(object: Callback<AddUserLoginResponse>
        {
            override fun onResponse(
                call: Call<AddUserLoginResponse>,
                response: Response<AddUserLoginResponse>
            ) {
                val serverResponse: AddUserLoginResponse? = response.body()
                if (serverResponse != null) {
                    if (response.isSuccessful && serverResponse.status == 0) {
                        Toast.makeText(this@MainActivity, serverResponse.message ?: "Success", Toast.LENGTH_LONG).show()
                        saveTheUserInfo(email, password)
                        Constants.USER_ID= response.body()!!.user?.user_id.toString()
                        Constants.USER_NAME= response.body()!!.user?.full_name.toString()
                        Constants.USER_EMAIL= response.body()!!.user?.email_id.toString()
                        Constants.USER_PHONE= response.body()!!.user?.mobile_no.toString()

                        val intent2 = Intent(this@MainActivity, SuperCartActivity::class.java)
                        startActivity(intent2)
                        finish()

                    } else {
                        Toast.makeText(this@MainActivity, "Incorrect password, please register . Error code: ${response.code()}", Toast.LENGTH_LONG).show()
                    }
                }
            }

            override fun onFailure(p0: Call<AddUserLoginResponse>, p1: Throwable) {
                Toast.makeText(this@MainActivity,"User email not found",Toast.LENGTH_LONG).show()
            }

        })
    }

    private fun validateInputs(emailTxt: String, passTxt: String): Boolean {
        if (emailTxt.isEmpty() || passTxt.isEmpty()) {
            Snackbar.make(binding.root, "Email or password can't be empty", Snackbar.LENGTH_SHORT).show()
            return false
        }

        val emailRegex = "^[\\w.-]+@[a-zA-Z\\d.-]+\\.[a-zA-Z]{2,6}$"
        val pattern = Pattern.compile(emailRegex)
        val matcher = pattern.matcher(emailTxt)

        if (!matcher.matches()) {
            Snackbar.make(binding.root, "Email is not valid", Snackbar.LENGTH_LONG)
                .setAction("Retry") {
                    binding.emailTxt.text?.clear()
                }
                .show()
            return false
        }

        if (passTxt.length < 6) {
            Snackbar.make(binding.root, "Password should be greater than 6 characters", Snackbar.LENGTH_SHORT).show()
            return false
        }

        return true
    }

    private fun checkIsUserAlreadyLoggedIn() {
        val isLoggedIn = SecuredSharedPreferenceManager.getBoolean(SecuredSharedPreferenceManager.IS_LOGGED_IN_SECURED)
        if (isLoggedIn) {
            moveToDashBoardScreen()
        }
    }

    private fun saveTheUserInfo(email: String, password: String) {
        SecuredSharedPreferenceManager.saveString(SecuredSharedPreferenceManager.USER_EMAIL_SECURED, email)
        SecuredSharedPreferenceManager.saveString(SecuredSharedPreferenceManager.PASSWORD_SECURED, password)
        SecuredSharedPreferenceManager.saveBooleanAndGetStatus(SecuredSharedPreferenceManager.IS_LOGGED_IN_SECURED, true)
        moveToDashBoardScreen()
    }

    private fun moveToDashBoardScreen() {
        startActivity(Intent(this, SuperCartActivity::class.java))
        finish()
    }
}
