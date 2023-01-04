package com.danscoding.inventoryapp

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Item(
    var itemName : String = "",
    var itemCategory : String = "",
    var itemPrice : String = ""
){
    @PrimaryKey(autoGenerate = true) var id : Int = 0
}