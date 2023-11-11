package com.example.applikacijazaispit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.applikacijazaispit.bazapodataka.BazaPodataka
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    private lateinit var nazivNamirniceEdit: EditText
    private lateinit var kalorijeEdit: EditText
    private lateinit var buttonSave: Button
    private lateinit var recyclerView: RecyclerView

    val bazaPodataka by lazy {
        Room.databaseBuilder(this, BazaPodataka::class.java, "baza-proizvoda").build()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        nazivNamirniceEdit = findViewById(R.id.etNamirnica)
        kalorijeEdit = findViewById(R.id.etCal)
        buttonSave = findViewById(R.id.btSave)


        recyclerView = findViewById(R.id.rvList)
        val layoutManager = LinearLayoutManager(this)
        val listaPodataka = mutableListOf<Proizvod>()
        val adapter = MojAdapter(listaPodataka)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = layoutManager

        GlobalScope.launch(Dispatchers.Main) {
            val proizvodi = dohvatiBazuPodataka().proizvodDao().sviProizvodi()
            listaPodataka.addAll(proizvodi)
            adapter.notifyDataSetChanged()
        }

        buttonSave.setOnClickListener {
            val namirnicaText = nazivNamirniceEdit.text.toString()
            val kalorijeText = kalorijeEdit.text.toString()

            if (namirnicaText.isNotEmpty() && kalorijeText.isNotEmpty()) {
                val noviProizvod = Proizvod(naziv = namirnicaText, kalorije = kalorijeText)
                val proizvodDao = bazaPodataka.proizvodDao()

                GlobalScope.launch(Dispatchers.IO) {
                    proizvodDao.unesiProizvod(noviProizvod)
                    val proizvodi = proizvodDao.sviProizvodi()

                    withContext(Dispatchers.Main) {
                        listaPodataka.clear()
                        listaPodataka.addAll(proizvodi)
                        adapter.notifyDataSetChanged()
                    }
                }
                nazivNamirniceEdit.text.clear()
                kalorijeEdit.text.clear()
            } else if (namirnicaText.isBlank() || kalorijeText.isBlank()) {
                nazivNamirniceEdit.error = getString(R.string.error_poruka)
                kalorijeEdit.error = getString(R.string.error_poruka)
            }
        }
    }

    private fun dohvatiBazuPodataka(): BazaPodataka {
        return bazaPodataka

    }
}