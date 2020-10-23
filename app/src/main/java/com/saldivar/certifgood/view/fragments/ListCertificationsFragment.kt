package com.saldivar.certifgood.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.saldivar.certifgood.R
import com.saldivar.certifgood.repo.objetos.Certification
import com.saldivar.certifgood.utils.CertificationObject
import com.saldivar.certifgood.utils.SwitchFragment
import com.saldivar.certifgood.view.adapter.CertificacionAdapter
import com.saldivar.certifgood.view.adapter.ListenerCertificationsAdapter
import com.saldivar.certifgood.viewModel.MainViewModel
import kotlinx.android.synthetic.main.fragment_list_certificaciones.view.*

class ListCertificationsFragment : Fragment() {
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
        recycler = rootview.recyclerview_certificaciones as RecyclerView
        recycler.layoutManager = LinearLayoutManager(context)
        SwitchFragment.numeroFragmentMostrado=1
            observeData()
        return rootview
    }

    companion object {
        fun newInstance(): ListCertificationsFragment = ListCertificationsFragment()
    }

    private fun observeData() {
            viewModel.getListCertificaciones().observe(this.viewLifecycleOwner, Observer {
                val list :MutableList<Certification> = it
                recycler.setHasFixedSize(true)
                recycler.itemAnimator = DefaultItemAnimator()
                recycler.layoutManager = layoutManager
                adapter= CertificacionAdapter(this.requireContext(),object :
                ListenerCertificationsAdapter{
                    override fun onClick(flight: Certification, position: Int) {
                        setearValores(flight,position,list)
                    }

                })
                recycler.adapter=adapter
                adapter.setListData(it)
                adapter.notifyDataSetChanged()
            })
    }

    private fun setearValores(
        flight: Certification,
        position: Int,
        list: MutableList<Certification>
    ) {
        with(flight){
            list[position].apply {
                CertificationObject.nombreCertificacion=nombre
                CertificationObject.cantidadPreguntas=cantidad_preguntas
                CertificationObject.niveles=niveles
            }
        }
        this.activity!!.supportFragmentManager.beginTransaction().apply{
            replace(R.id.container_fragment_certificaciones,LevelsFragment.newInstance())
            setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            addToBackStack(null)
            commit()


    }}
}