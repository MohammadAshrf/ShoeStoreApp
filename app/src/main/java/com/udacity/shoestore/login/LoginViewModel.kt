package com.udacity.shoestore.login

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LoginViewModel : ViewModel() {
    private val userEmail = MutableLiveData<String>()
    private val userPassword = MutableLiveData<String>()

    private val _loginSuccess = MutableLiveData<Boolean>()
    val loginSuccess: LiveData<Boolean>
        get() = _loginSuccess

    private val _loginFailed = MutableLiveData<Boolean>()
    val loginFailed: LiveData<Boolean>
        get() = _loginFailed

    fun login() {
        if (Valid.validLogin(userEmail.value, userPassword.value)) {
            _loginFailed.value = false
            Const.Email = userEmail.value.toString()
            Const.Password = userPassword.value.toString()
            _loginSuccess.value = true
        } else _loginFailed.value = true
    }

    object Const {
        var Email = ""
        var Password = ""
    }

    object Valid {
        fun validLogin(email: String? = "", password: String? = ""): Boolean {
            return Patterns.EMAIL_ADDRESS.matcher(email.toString()).matches()
                    && !(password.isNullOrEmpty())
        }
    }
}