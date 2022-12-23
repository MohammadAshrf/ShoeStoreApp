package com.udacity.shoestore.shoeList

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.udacity.shoestore.models.Shoe

class ShoeViewModel : ViewModel() {
    val shoeName = MutableLiveData<String>()
    val shoeBrand = MutableLiveData<String>()
    val shoeSize = MutableLiveData<String>()
    val shoeDescription = MutableLiveData<String>()

    private var _mutableShoeList = mutableListOf<Shoe>()


    private val _savedSuccessfully = MutableLiveData<Boolean>()
    val savedSuccessfully: LiveData<Boolean>
        get() = _savedSuccessfully

    private val _savedFailed = MutableLiveData<Boolean>()
    val savedFailed: LiveData<Boolean>
        get() = _savedFailed

    private val _cancel = MutableLiveData<Boolean>()
    val cancel: LiveData<Boolean>
        get() = _cancel


    private var _myShoeList = MutableLiveData<List<Shoe>>()
    val myShoeList: LiveData<List<Shoe>>
        get() = _myShoeList


    fun saveShoe() {
        if (shoeName.value.isNullOrEmpty() || shoeBrand.value.isNullOrEmpty() ||
            shoeSize.value.isNullOrEmpty() || shoeDescription.value.isNullOrEmpty()
        ) {
            _savedFailed.value = true
        } else {
            _savedFailed.value = false
            _mutableShoeList.add(
                Shoe(
                    name = shoeName.value, brand = shoeBrand.value, size = shoeSize.value,
                    description = shoeDescription.value
                )
            )
            _myShoeList.value = _mutableShoeList
            updateSuccessState(true)
            clearData()
        }
    }

    fun updateSuccessState(success: Boolean) {
        _savedSuccessfully.value = success
    }

    fun cancelProcess(cancel: Boolean) {
        _cancel.value = cancel
        clearData()
    }

    private fun clearData() {
        shoeName.value = ""
        shoeBrand.value = ""
        shoeSize.value = ""
        shoeDescription.value = ""
    }


}