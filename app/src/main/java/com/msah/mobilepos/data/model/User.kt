package com.msah.mobilepos.data.model

import android.text.TextUtils
import android.util.Patterns
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.Date

data class User(
    var email: String? = "",
    var password: String? = "",
    var passwordAgain: String? = "",
    var username: String? = "",
    var uid: String? = "",
    var address: String? = "",
    var orders: String? = "",
    var dateJoined: LocalDate =  LocalDate.now()

) {

    fun isSignInFieldEmpty() : Boolean{
        return TextUtils.isEmpty(email) || TextUtils.isEmpty(password)
    }

    fun isSignUpFieldEmpty() : Boolean{
        return TextUtils.isEmpty(email) || TextUtils.isEmpty(password) || TextUtils.isEmpty(passwordAgain) || TextUtils.isEmpty(username)
    }

    fun isValidEmail() : Boolean{
        return Patterns.EMAIL_ADDRESS.matcher(email!!).matches()
    }

    fun isPasswordGreaterThan5() : Boolean{
        return password!!.length > 5
    }

    fun isPasswordMatch() : Boolean{
        return password == passwordAgain
    }

}