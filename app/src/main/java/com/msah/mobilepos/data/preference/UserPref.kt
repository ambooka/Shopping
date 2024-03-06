package com.msah.mobilepos.data.preference

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.msah.mobilepos.utils.Constants
import kotlinx.coroutines.flow.first
import java.time.LocalDate

class UserPref(var context: Context) {

    companion object{
        val Context.ds : DataStore<Preferences> by preferencesDataStore(Constants.DATA_STORE_USER)
        val KEY_USERNAME = stringPreferencesKey("USERNAME")
        val KEY_EMAIL = stringPreferencesKey("EMAIL")
        val KEY_IS_FIRST_USAGE = booleanPreferencesKey("IS_FIRST_USAGE")
        val KEY_PHONE = stringPreferencesKey("PHONE")
        val KEY_DATE = stringPreferencesKey("DATE")
        val KEY_ADDRESS = stringPreferencesKey("ADDRESS")
        val KEY_ORDERS = stringPreferencesKey("ORDERS")
        val KEY_TYPE = stringPreferencesKey("TYPE")


    }
    suspend fun setUsername(username:String){
        context.ds.edit {
            it[KEY_USERNAME] = username
        }
    }



    suspend fun getUsername():String{
        val p = context.ds.data.first()
        return p[KEY_USERNAME] ?: ""
    }



    suspend fun setAccType(type:String){
        context.ds.edit {
            it[KEY_TYPE] = type
        }
    }



    suspend fun getAccType():String{
        val p = context.ds.data.first()
        return p[KEY_TYPE] ?: ""
    }


    suspend fun setEmail(email:String){
        context.ds.edit {
            it[KEY_EMAIL] = email
        }
    }


    suspend fun setPhone(username:String){
        context.ds.edit {
            it[KEY_PHONE] = username
        }
    }



    suspend fun getPhone():String{
        val p = context.ds.data.first()
        return p[KEY_PHONE] ?: ""
    }


    suspend fun setAddress(address:String){
        context.ds.edit {
            it[KEY_ADDRESS] = address
        }
    }




    suspend fun getOrders():String{
        val p = context.ds.data.first()
        return p[KEY_ORDERS] ?: ""
    }

    suspend fun setOrders(orders:String){
        context.ds.edit {
            it[KEY_ORDERS] = orders
        }
    }

    suspend fun setDateJoined(dateJoined:String){
        context.ds.edit {
            it[KEY_DATE] = dateJoined.toString()
        }
    }

    suspend fun getEmail():String{
        val p = context.ds.data.first()
        return p[KEY_EMAIL] ?: ""
    }

    suspend fun setFirstUsage(value:Boolean){
        context.ds.edit {
            it[KEY_IS_FIRST_USAGE] = value
        }
    }

    suspend fun isFirstUsage():Boolean{
        val p = context.ds.data.first()
        return p[KEY_IS_FIRST_USAGE] ?: true
    }

    suspend fun clearAllPreference() {
        context.ds.edit {
            it.clear()
        }
        setFirstUsage(false)
    }






}