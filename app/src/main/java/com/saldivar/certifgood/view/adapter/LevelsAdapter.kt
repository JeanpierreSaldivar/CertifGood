package com.saldivar.certifgood.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.saldivar.certifgood.R
import com.saldivar.certifgood.utils.CertificationObject
import kotlinx.android.synthetic.main.item_recycler_niveles.view.*

class LevelsAdapter(private val context: Context, private val listener:ListenerLevelsAdapter):RecyclerView.Adapter<LevelsAdapter.MainViewHolder>() {
    private var dataList = mutableListOf<Int>()
    fun setListData(){
        for(x in 1..CertificationObject.niveles){
            dataList.add(x)
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): LevelsAdapter.MainViewHolder {
        val view:View = LayoutInflater.from(context).inflate(R.layout.item_recycler_niveles,parent,false)
        return MainViewHolder(view)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        val certificacion = dataList[position]
        holder.bin(certificacion,listener)
    }

    override fun getItemCount(): Int =dataList.size

    class MainViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        fun bin(certificacion: Int,listener: ListenerLevelsAdapter)= with(itemView){
            texto_niveles.text = "Nivel $certificacion"
            item_niveles.setOnClickListener { listener.onClick(certificacion,adapterPosition) }
        }
    }

}