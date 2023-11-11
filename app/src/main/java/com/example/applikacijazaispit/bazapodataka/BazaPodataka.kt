package com.example.applikacijazaispit.bazapodataka

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.applikacijazaispit.Proizvod

@Database(entities = [Proizvod::class], version = 1)
abstract class BazaPodataka : RoomDatabase() {
    abstract fun proizvodDao(): ProizvodDao
}