package com.saldivar.certifgood.view.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.saldivar.certifgood.R
import com.saldivar.certifgood.repo.objetos.Certificacion
import com.saldivar.certifgood.utils.CertificacionO
import com.saldivar.certifgood.utils.SwitchFragment
import com.saldivar.certifgood.view.activitys.CertificacionesActivity
import com.saldivar.certifgood.view.adapter.CertificacionAdapter
import com.saldivar.certifgood.view.adapter.ListenerCertificacionesAdapter
import com.saldivar.certifgood.view.adapter.ListenerNivelesAdapter
import com.saldivar.certifgood.view.adapter.NivelesAdapter
import com.saldivar.certifgood.viewModel.MainViewModel
import kotlinx.android.synthetic.main.fragment_list_certificaciones.view.*
import kotlinx.android.synthetic.main.fragment_niveles.view.*

class NivelesFragment : Fragment() {
    private lateinit var recycler: RecyclerView
    private lateinit var adapter: NivelesAdapter
    private val layoutManager by lazy { LinearLayoutManager(context,
        LinearLayoutManager.VERTICAL,false)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val rootview= inflater.inflate(R.layout.fragment_niveles, container, false)
        recycler = rootview.recyclerview_niveles as RecyclerView
        recycler.layoutManager = LinearLayoutManager(context)
        SwitchFragment.numeroFragmentMostrado=2
        observeData()
        return rootview
    }
    companion object {
        fun newInstance(): NivelesFragment = NivelesFragment()
    }

    private fun observeData() {
        recycler.setHasFixedSize(true)
        recycler.itemAnimator = DefaultItemAnimator()
        recycler.layoutManager = layoutManager
        adapter= NivelesAdapter(this.requireContext(),object :
            ListenerNivelesAdapter {
            override fun onClick(flight: Int, position: Int) {
                Log.d("kkkk","$position")
            }

        })
        recycler.adapter=adapter
        adapter.setListData()
        adapter.notifyDataSetChanged()

    }


}