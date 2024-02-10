package com.msah.mobilepos.data.repository.auth

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.msah.mobilepos.data.model.User
import com.msah.mobilepos.data.repository.auth.AuthRepository

class AuthRepositoryImpl : AuthRepository {

    override fun signIn(user: User): Task<AuthResult> {
        return FirebaseAuth.getInstance().signInWithEmailAndPassword(user.email!!, user.password!!)
    }

    override fun signUp(user: User): Task<AuthResult> {
        return FirebaseAuth.getInstance().createUserWithEmailAndPassword(user.email!!, user.password!!)
    }

}