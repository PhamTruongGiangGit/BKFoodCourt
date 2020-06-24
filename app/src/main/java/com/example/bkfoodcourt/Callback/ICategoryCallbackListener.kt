package com.example.bkfoodcourt.Callback

import com.example.bkfoodcourt.Model.CategoryModel
import com.example.bkfoodcourt.Model.PopularCategoryModel

interface ICategoryCallbackListener {
    fun onCategorySuccess(categoriesList:List<CategoryModel>)
    fun onCategoryLoadFailed(message:String)
}