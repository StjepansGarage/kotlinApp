package com.example.applikacijazaispit

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.applikacijazaispit.bazapodataka.BazaPodataka
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MojAdapter(private val listaPodataka: MutableList<Proizvod>) :
    RecyclerView.Adapter<MojViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MojViewHolder {
        val prikazElemntaListe =
            LayoutInflater.from(parent.context).inflate(R.layout.element_liste, parent, false)
        return MojViewHolder(itemView = prikazElemntaListe)
    }

    override fun getItemCount(): Int {
        return listaPodataka.size
    }

    override fun onBindViewHolder(holder: MojViewHolder, position: Int) {
        val proizvod = listaPodataka[position]

        holder.nazivNamirniceText.text = proizvod.naziv
        holder.kalorijeText.text = proizvod.kalorije + " kcal"

        holder.itemView.setOnClickListener {
            prikazDialogaZaBrisanje(holder.itemView.context, position)
        }
    }

    private fun prikazDialogaZaBrisanje(context: Context, position: Int) {
        val builder = AlertDialog.Builder(context)
        builder.setTitle("Potvrda brisanja")
            .setMessage("Da li ste sigurni da želite obrisati ovaj element?")
            .setPositiveButton("Da") { _, _ ->
                ukloniElement(context, position)
            }
            .setNegativeButton("Ne") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

    fun dohvatiBazuPodataka(context: Context): BazaPodataka {
        return Room.databaseBuilder(context, BazaPodataka::class.java, "baza-proizvoda").build()
    }

    fun ukloniElement(context: Context, position: Int) {
        if (position in 0 until listaPodataka.size) {
            val obrisiProizvod = listaPodataka[position]

            GlobalScope.launch(Dispatchers.IO) {
                dohvatiBazuPodataka(context).proizvodDao().obrisiProizvod(obrisiProizvod)
            }

            listaPodataka.removeAt(position)
            notifyItemRemoved(position)
            notifyItemRangeChanged(position, listaPodataka.size)
        }
    }
}
