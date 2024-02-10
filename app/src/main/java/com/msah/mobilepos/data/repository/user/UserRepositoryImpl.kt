package com.msah.mobilepos.data.repository.user

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.msah.mobilepos.data.model.User
import com.msah.mobilepos.utils.Constants
import com.msah.mobilepos.utils.Constants.DATABASE_FIELD_UID
import com.msah.mobilepos.utils.Constants.DATABASE_FIELD_USERNAME

class UserRepositoryImpl : UserRepository {

    override fun getUserData(user: User): DocumentReference {

        return Firebase.firestore.collection(Constants.DATABASE_USERS_TABLE)
            .document(user.uid!!)

    }

    override fun userAddDatabase(user: User): Task<Void> {

        val userMap = hashMapOf(
            DATABASE_FIELD_USERNAME to user.username,
            DATABASE_FIELD_UID to user.uid,
        )

        return Firebase.firestore.collection(Constants.DATABASE_USERS_TABLE)
            .document(user.uid.toString())
            .set(userMap)

    }

}