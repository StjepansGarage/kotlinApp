package com.example.applikacijazaispit.bazapodataka

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.applikacijazaispit.Proizvod

@Dao
interface ProizvodDao {
    @Query("SELECT * FROM proizvod")
    suspend fun sviProizvodi(): MutableList<Proizvod>
    @Insert
    suspend fun unesiProizvod(proizvod: Proizvod)
    @Update
    suspend fun osvjeziProizvod(proizvod: Proizvod)
    @Delete
    suspend fun obrisiProizvod(proizvod: Proizvod)
}