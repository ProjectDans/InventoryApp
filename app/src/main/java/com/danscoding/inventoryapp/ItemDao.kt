package com.danscoding.inventoryapp

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update


@Dao
interface ItemDao {

    @Insert
    suspend fun addItem(item: Item)

    @Query("SELECT * FROM item ORDER BY id DESC")
    suspend fun getAllItem(): List<Item>

    @Update
    suspend fun updateItem(item: Item)

    @Delete
    suspend fun deleteItem(item: Item)

}