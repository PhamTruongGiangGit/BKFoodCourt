package com.example.bkfoodcourt.Common

import com.example.bkfoodcourt.Model.CategoryModel
import com.example.bkfoodcourt.Model.FoodModel
import com.example.bkfoodcourt.Model.UserModel

object Common {
    var foodSelected: FoodModel?=null
    var categorySelected: CategoryModel?=null
    val CATEGORY_REF: String = "Category"
    val FULL_WIDTH_CONLUMN: Int = 1
    val DEFAULT_COLUMN_COUNT: Int = 0
    val POPULAR_REF: String="MostPopular"
    val BEST_DEALS_REF: String="BestDeals"
    val USER_REFERENCE="Users"
    var currentUser:UserModel?=null
}