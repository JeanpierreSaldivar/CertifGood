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
import com.saldivar.certifgood.view.adapter.CertificacionAdapter
import com.saldivar.certifgood.viewModel.MainViewModel
import kotlinx.android.synthetic.main.fragment_list_certificaciones.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ListCertificacionesFragment : Fragment() {
    private val viewModel by lazy{ ViewModelProvider(this).get(MainViewModel::class.java)}
    private lateinit var recycler: RecyclerView
    private lateinit var adapter: CertificacionAdapter
    private val layoutManager by lazy { LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)}

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val rootview = inflater.inflate(R.layout.fragment_list_certificaciones, container, false)
        adapter= CertificacionAdapter(this.requireContext())
        recycler = rootview.recyclerview_certificaciones as RecyclerView
        recycler.layoutManager = LinearLayoutManager(context)
        recycler.adapter=adapter
        CoroutineScope(Dispatchers.IO).launch {
            observeData()
        }
        return rootview
    }

    companion object {
        fun newInstance(): ListCertificacionesFragment = ListCertificacionesFragment()
    }

    private fun observeData() {
            viewModel.getListCertificaciones().observe(this.viewLifecycleOwner, Observer {
                recycler.setHasFixedSize(true)
                recycler.itemAnimator = DefaultItemAnimator()
                recycler.layoutManager = layoutManager
                adapter.setListData(it)
                adapter.notifyDataSetChanged()

            })

    }
}