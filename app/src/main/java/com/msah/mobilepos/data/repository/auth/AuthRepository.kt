package com.msah.mobilepos.data.repository.auth

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.msah.mobilepos.data.model.User

interface AuthRepository {

    fun signIn(user: User): Task<AuthResult>

    fun signUp(user: User): Task<AuthResult>

}