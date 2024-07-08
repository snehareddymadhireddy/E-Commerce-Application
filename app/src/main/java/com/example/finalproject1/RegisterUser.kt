package com.example.finalproject1

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.finalproject1.databinding.ActivityRegisterUserBinding

class RegisterUser : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterUserBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       binding= ActivityRegisterUserBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViews()
    }

    private fun initViews() {
        with(binding)
        {
            var name=etFullName.text.toString()
            var mobileNo=etMobileNo.text.toString()
            var emailId=etEmailId.text.toString()
            var password=etPassword.text.toString()
            saveTheUserInfo(name,mobileNo,emailId,password)
        }

    }
    private fun saveTheUserInfo(name:String, mobileNo:String,email: String, password: String) {
        SecuredSharedPreferenceManager.saveString(SecuredSharedPreferenceManager.NAME_SECURED,name)
        SecuredSharedPreferenceManager.saveString(SecuredSharedPreferenceManager.MOBILE_NUMBER_SECURED,mobileNo)
        SecuredSharedPreferenceManager.saveString(SecuredSharedPreferenceManager.USER_EMAIL_SECURED, email)
        SecuredSharedPreferenceManager.saveString(SecuredSharedPreferenceManager.PASSWORD_SECURED, password)
        SecuredSharedPreferenceManager.saveBooleanAndGetStatus(SecuredSharedPreferenceManager.IS_LOGGED_IN_SECURED, true)
        moveToDashBoardScreen()
    }

    private fun moveToDashBoardScreen() {
       startActivity(Intent(this,SuperCartActivity::class.java))
        finish()
    }

}