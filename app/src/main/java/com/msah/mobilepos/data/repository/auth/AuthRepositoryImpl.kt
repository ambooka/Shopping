package com.msah.mobilepos.data.repository.auth

import android.util.Log
import android.widget.Toast
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.msah.mobilepos.R
import com.msah.mobilepos.data.model.User
import com.msah.mobilepos.data.repository.auth.AuthRepository
import com.msah.mobilepos.main.employee.EmployeeFragment

class AuthRepositoryImpl : AuthRepository {

    override fun signIn(user: User): Task<AuthResult> {

        return FirebaseAuth.getInstance().signInWithEmailAndPassword(user.email!!, user.password!!)
    }

    override fun signUp(user: User): Task<AuthResult> {

        return   FirebaseAuth.getInstance().createUserWithEmailAndPassword(user.email!!, user.password!!)

    }
}