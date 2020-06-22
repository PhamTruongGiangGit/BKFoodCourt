package com.example.bkfoodcourt.Database

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Cart")

class CartItem {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "foodId")
    var foodId:String?=null

    @ColumnInfo(name = "foodName")
    var foodName:String?=null

    @ColumnInfo(name = "foodPrice")
    var foodPrice:Double=0.0

    @ColumnInfo(name = "foodQuantity")
    var foodQuantity:Int=0

    @ColumnInfo(name = "uid")
    var uid:String?=null

    /** CAN ADD ADD ON FOR FOOD OR USER PHONE LATER **/
}