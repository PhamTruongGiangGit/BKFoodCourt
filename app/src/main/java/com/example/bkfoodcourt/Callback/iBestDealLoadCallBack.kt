package com.example.bkfoodcourt.Callback

import com.example.bkfoodcourt.Model.BestDealModel
import com.example.bkfoodcourt.Model.PopularCategoryModel

interface iBestDealLoadCallBack {
    fun onBestDealLoadSuccess(bestDealList:List<BestDealModel>)
    fun onbBestDealLoadFailed(message:String)
}