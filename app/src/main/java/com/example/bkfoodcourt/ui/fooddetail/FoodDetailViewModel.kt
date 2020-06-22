package com.example.bkfoodcourt.ui.fooddetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.bkfoodcourt.Common.Common.foodSelected
import com.example.bkfoodcourt.Model.FoodModel
import com.google.android.gms.common.internal.service.Common

class FoodDetailViewModel : ViewModel() {

    private var mutableLiveDataFood:MutableLiveData<FoodModel>?=null

    fun getMutableLiveDataFood():MutableLiveData<FoodModel> {
        if (mutableLiveDataFood == null) {
            mutableLiveDataFood = MutableLiveData()

        }
        mutableLiveDataFood!!.value = com.example.bkfoodcourt.Common.Common.foodSelected
        return mutableLiveDataFood!!
    }
}