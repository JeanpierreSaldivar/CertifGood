package com.saldivar.certifgood.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.saldivar.certifgood.R
import com.saldivar.certifgood.repo.objetos.Certificacion
import com.saldivar.certifgood.utils.CertificacionO
import com.saldivar.certifgood.viewModel.MainViewModel
import kotlinx.android.synthetic.main.item_recycler_certificaciones.view.*

class CertificacionAdapter(private val context:Context,private val listener:ListenerCertificacionesAdapter):RecyclerView.Adapter<CertificacionAdapter.MainViewHolder>() {
    private var dataList = mutableListOf<Certificacion>()
    fun setListData(data:MutableList<Certificacion>){
        dataList = data
    }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CertificacionAdapter.MainViewHolder {
        val view:View = LayoutInflater.from(context).inflate(R.layout.item_recycler_certificaciones,parent,false)
        return MainViewHolder(view)
    }

    override fun onBindViewHolder(holder: CertificacionAdapter.MainViewHolder, position: Int) {
        val certificacion = dataList[position]
        holder.bin(certificacion,listener)
    }

    override fun getItemCount(): Int =dataList.size

    class MainViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        fun bin(certificacion: Certificacion,listener: ListenerCertificacionesAdapter)= with(itemView){
            texto_certificacion.text = "Certificación ${certificacion.nombre}"
            item_certificacion.setOnClickListener { listener.onClick(certificacion,adapterPosition) }
        }
    }
}