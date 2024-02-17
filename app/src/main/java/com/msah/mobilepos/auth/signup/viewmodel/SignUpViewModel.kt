package com.msah.mobilepos.auth.signup.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.msah.mobilepos.R
import com.msah.mobilepos.data.model.DataState
import com.msah.mobilepos.data.model.User
import com.msah.mobilepos.data.repository.auth.AuthRepository
import com.msah.mobilepos.data.repository.user.UserRepository
import java.time.LocalDate

class SignUpViewModel(
    private val authRepository: AuthRepository,
    private val userRepository: UserRepository
) : ViewModel() {

    val userLiveData = MutableLiveData<DataState<User>>()
    private lateinit var user: User

    fun onSignUpClicked(username: String, email: String, address: String, password: String, passwordAgain: String){

        userLiveData.value = DataState.Loading()
        user = User(email, password, passwordAgain, username, address, orders = "0", dateJoined = LocalDate.now())
        checkFields()

    }

    private fun checkFields(){

        if(user.isSignUpFieldEmpty()){
            userLiveData.value = DataState.Error(R.string.fields_cannot_empty.toString())
            return
        }

        if(!user.isValidEmail()){
            userLiveData.value = DataState.Error(R.string.enter_valid_email.toString())
            return
        }

        if(!user.isPasswordMatch()){
            userLiveData.value = DataState.Error(R.string.passwords_dont_match.toString())
            return
        }

        if(!user.isPasswordGreaterThan5()){
            userLiveData.value = DataState.Error(R.string.password_greater_than_5.toString())
            return
        }

        signUp()

    }

    private fun signUp(){

        authRepository.signUp(user)
            .addOnSuccessListener {
                user.uid = it.user!!.uid
                userAddDatabase()
            }
            .addOnFailureListener { e ->
                userLiveData.value = DataState.Error(e.message!!)
            }

    }

    private fun userAddDatabase(){

        userRepository.userAddDatabase(user)
            .addOnSuccessListener {
                userLiveData.value = DataState.Success(user)
            }
            .addOnFailureListener { e ->
                userLiveData.value = DataState.Error(e.message!!)
            }

    }

}