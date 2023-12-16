package com.jspadda.contactsmanager.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "contacts")
data class User(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "user_id")
    val id : Int,
    @ColumnInfo(name = "user_name")
    var name: String,
    @ColumnInfo(name = "user_email")
    var email: String
)