package com.saldivar.certifgood.view.adapter

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.drawable.ColorDrawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.saldivar.certifgood.R
import com.saldivar.certifgood.repo.objetos.Historial
import com.saldivar.certifgood.utils.loadByResourcePicaso
import kotlinx.android.synthetic.main.item_recycler_hitorial.view.*

class HistorialAdapter(private val context: Context): RecyclerView.Adapter<HistorialAdapter.MainViewHolder>() {
    private var dataList = mutableListOf<Historial>()
    fun setListData(data:MutableList<Historial>){
        dataList = data
    }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MainViewHolder {
        val view: View = LayoutInflater.from(context).inflate(R.layout.item_recycler_hitorial,parent,false)
        return MainViewHolder(view)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        val historial = dataList[position]
        holder.bin(historial)
    }

    override fun getItemCount():Int =dataList.size

    class MainViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        fun bin(historial: Historial)= with(itemView){
            if(historial.estado_examen){
                carita_historial.background =resources.getDrawable(R.drawable.ic_carita_buena)
                item_niveles.setCardBackgroundColor(resources.getColor(R.color.color1))
            }else{
                carita_historial.background =resources.getDrawable(R.drawable.ic_carita_triste)
                item_niveles.setCardBackgroundColor(resources.getColor(R.color.fux))
            }
            texto_titulo.text = "Certificación: ${historial.nombre_examen}"
            texto_porcentaje.text = "Porcentaje de acierto: ${historial.porcentaje_examen}%"
            texto_nota.text = "Nota: ${historial.nota_examen}"
        }
    }
}