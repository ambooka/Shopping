package com.msah.mobilepos.data.model

import android.os.Parcelable
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import java.time.LocalDate


@Parcelize
data class Product(
    @SerializedName("name")
    var name: String? = "",
    @SerializedName("description")
    var description: String? = "",
    @SerializedName("id")
    var id: String? = "",
    @SerializedName("price")
    var price: Double? = 0.0,
    @SerializedName("quantity")
    var quantity: Int = 0,
    @SerializedName("imgUrl")
    var imgUrl: String = ""
) : Parcelable {

    // json convert method
    fun toJson(): String {
        return Gson().toJson(this)
    }

    // static json object
    companion object {
        fun fromJson(jsonValue: String): Product {
            return Gson().fromJson(jsonValue, Product::class.java)
        }
    }

}
