package com.udacity.shoestore.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize

data class Shoe(var name :String? = "", var brand : String? = "", var size:String? = "",
                var description: String? = "") : Parcelable