package com.saldivar.certifgood.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.saldivar.certifgood.R
import com.saldivar.certifgood.repo.objetos.Certification
import com.saldivar.certifgood.repo.objetos.Historial
import com.saldivar.certifgood.utils.*
import com.saldivar.certifgood.view.activitys.CertificationsActivity
import com.saldivar.certifgood.view.adapter.CertificacionAdapter
import com.saldivar.certifgood.view.adapter.HistorialAdapter
import com.saldivar.certifgood.view.adapter.ListenerCertificationsAdapter
import com.saldivar.certifgood.viewModel.MainViewModel
import com.saldivar.zkflol.utils.permissionsAndConexion.CheckInternetConnection
import kotlinx.android.synthetic.main.activity_certificaciones.*
import kotlinx.android.synthetic.main.fragment_historial.*
import kotlinx.android.synthetic.main.fragment_historial.view.*
import kotlinx.android.synthetic.main.fragment_list_certificaciones.view.*


class HistorialFragment : Fragment() {
    private val viewModel by lazy{ ViewModelProvider(this).get(MainViewModel::class.java)}
    private lateinit var recycler: RecyclerView
    private lateinit var adapter: HistorialAdapter
    private val layoutManager by lazy { LinearLayoutManager(context,
        LinearLayoutManager.VERTICAL,false)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val rootview = inflater.inflate(R.layout.fragment_historial, container, false)
        this.activity!!.text_certifiacion.text = "Historial de evaluaciones:"
        adapter= HistorialAdapter(this.requireContext())
        recycler = rootview.recycler_historial as RecyclerView
        recycler.layoutManager = LinearLayoutManager(context)
        recycler.adapter=adapter
        SwitchFragment.numeroFragmentMostrado=2
        getDataHistorial()
        return rootview
    }
    companion object {
        fun newInstance(): HistorialFragment = HistorialFragment()
    }

    private fun getDataHistorial() {
        val prefs = preferencesSaldivar(this.activity!!,0,"Datos_Usuario")
        val user = prefs.getString("email_User", CredentialsLogin.usuario)!!
        if(CheckInternetConnection.validateInternetConnection(this.activity!!)){
            viewModel.getHistorial(user).observe(this.viewLifecycleOwner, Observer {
                recycler.setHasFixedSize(true)
                recycler.itemAnimator = DefaultItemAnimator()
                recycler.layoutManager = layoutManager
                adapter.setListData(it)
                adapter.notifyDataSetChanged()
            })
        }else{
            ShowDialog.dialogShow("Compruebe su conexion a internet", this.activity!!)
        }

    }
}