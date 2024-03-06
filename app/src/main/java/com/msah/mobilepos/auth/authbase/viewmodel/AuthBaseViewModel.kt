package com.msah.mobilepos.auth.authbase.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.msah.mobilepos.R
import com.msah.mobilepos.auth.employeeLogin.EmployeeLoginFragment
import com.msah.mobilepos.data.model.TabLayoutFragment
import com.msah.mobilepos.auth.signin.SignInFragment
import com.msah.mobilepos.auth.signup.SignUpFragment

class AuthBaseViewModel : ViewModel() {

    val fragmentLiveData = MutableLiveData<TabLayoutFragment>()

    init {
        initFragments()
    }

    private fun initFragments(){

        val fragments = TabLayoutFragment(
            listOf(
                SignInFragment(),
                SignUpFragment(),
                EmployeeLoginFragment()
            ),
            listOf(
                R.string.signin,
                R.string.signup,
                R.string.staff
            )
        )

        fragmentLiveData.value = fragments

    }

}