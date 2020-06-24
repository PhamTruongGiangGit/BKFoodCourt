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

    @ColumnInfo(name="userPhone")
    var userPhone:String?=null

    @ColumnInfo(name="foodImage")
    var foodImage:String?=null

    @ColumnInfo(name="foodExtraPrice")
    var foodExtraPrice:Double?=null

    @ColumnInfo(name="foodAddon")
    var foodAddon:String?=null

    @ColumnInfo(name="foodSize")
    var foodSize:String?=null


    /** CAN ADD ADD ON FOR FOOD OR USER PHONE LATER **/
}