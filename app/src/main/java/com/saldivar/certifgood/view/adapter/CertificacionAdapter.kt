package com.saldivar.certifgood.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.saldivar.certifgood.R
import com.saldivar.certifgood.repo.objetos.Certification
import kotlinx.android.synthetic.main.item_recycler_certificaciones.view.*

class CertificacionAdapter(private val context:Context,private val listener:ListenerCertificationsAdapter):RecyclerView.Adapter<CertificacionAdapter.MainViewHolder>() {
    private var dataList = mutableListOf<Certification>()
    fun setListData(data:MutableList<Certification>){
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
        fun bin(certificacion: Certification, listener: ListenerCertificationsAdapter)= with(itemView){
            texto_certificacion.text = "Certificación ${certificacion.nombre}"
            item_certificacion.setOnClickListener { listener.onClick(certificacion,adapterPosition) }
        }
    }
}