package com.example.finalproject1

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.finalproject1.databinding.ActivityMainBinding
import java.util.regex.Pattern

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViews()
        newRegistration()
    }

    private fun newRegistration() {
        with(binding)
        {
            register.setOnClickListener {
                val intent=Intent(this@MainActivity,RegisterUser::class.java)
                startActivity(intent)
                finish()
            }
        }
    }

    private fun initViews() {
        checkIsUserAlreadyLoggedIn()
        with(binding) {
            btnlogin.setOnClickListener {
                val email = emailTxt.text.toString()
                val password = passTxt.text.toString()

                if (validateInputs(email, password)) {
                    saveTheUserInfo(email, password)
                    val intent2 = Intent(this@MainActivity, SuperCartActivity::class.java)
                    startActivity(intent2)
                    finish()
                }
            }
        }
    }

    private fun validateInputs(emailTxt: String, passTxt: String): Boolean {
        if (emailTxt.isEmpty() || passTxt.isEmpty()) {
            Toast.makeText(this@MainActivity, "Email or password can't be empty", Toast.LENGTH_SHORT).show()
            return false
        }

        val emailRegex = "^[\\w.-]+@[a-zA-Z\\d.-]+\\.[a-zA-Z]{2,6}$"
        val pattern = Pattern.compile(emailRegex)
        val matcher = pattern.matcher(emailTxt)

        if (!matcher.matches()) {
            Toast.makeText(this@MainActivity, "Email is not valid", Toast.LENGTH_SHORT).show()
            return false
        }

        if (passTxt.length < 6) {
            Toast.makeText(this@MainActivity, "Password should be greater than 6 characters", Toast.LENGTH_SHORT).show()
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
