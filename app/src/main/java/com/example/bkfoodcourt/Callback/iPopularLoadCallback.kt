package com.example.bkfoodcourt.Callback

import com.example.bkfoodcourt.Model.PopularCategoryModel

interface iPopularLoadCallback {
    fun onPopularLoadSuccess(popularModelList:List<PopularCategoryModel>)
    fun onPopularLoadFailed(message:String)
}