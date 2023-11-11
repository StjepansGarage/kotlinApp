package com.example.applikacijazaispit

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Proizvod(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val naziv: String,
    val kalorije: String
)
