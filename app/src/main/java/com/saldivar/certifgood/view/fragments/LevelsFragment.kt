package com.saldivar.certifgood.view.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.saldivar.certifgood.R
import com.saldivar.certifgood.utils.CertificationObject
import com.saldivar.certifgood.utils.ShowDialog
import com.saldivar.certifgood.utils.SwitchFragment
import com.saldivar.certifgood.view.activitys.CertificationsActivity
import com.saldivar.certifgood.view.activitys.QuestionsActivity
import com.saldivar.certifgood.view.adapter.ListenerLevelsAdapter
import com.saldivar.certifgood.view.adapter.LevelsAdapter
import com.saldivar.zkflol.utils.permissionsAndConexion.CheckInternetConnection
import kotlinx.android.synthetic.main.fragment_niveles.*
import kotlinx.android.synthetic.main.fragment_niveles.view.*

class LevelsFragment : Fragment() {
    private lateinit var recycler: RecyclerView
    private lateinit var adapter: LevelsAdapter
    private val layoutManager by lazy { LinearLayoutManager(context,
        LinearLayoutManager.VERTICAL,false)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val rootview= inflater.inflate(R.layout.fragment_niveles, container, false)
        rootview.totalNivelestxt.text = "Total ${CertificationObject.niveles} niveles"
        recycler = rootview.recyclerview_niveles as RecyclerView
        recycler.layoutManager = LinearLayoutManager(context)
        SwitchFragment.numeroFragmentMostrado=2
        observeData()
        return rootview
    }
    companion object {
        fun newInstance(): LevelsFragment = LevelsFragment()
    }

    private fun observeData() {
        recycler.setHasFixedSize(true)
        recycler.itemAnimator = DefaultItemAnimator()
        recycler.layoutManager = layoutManager
        adapter= LevelsAdapter(this.requireContext(),object :
            ListenerLevelsAdapter {
            override fun onClick(flight: Int, position: Int) {
                CertificationObject.nivelElegido = (position+1).toString()
                nextActivity()
            }

        })
        recycler.adapter=adapter
        adapter.setListData()
        adapter.notifyDataSetChanged()

    }

    private fun nextActivity() {
        val activity = this.activity!!
        if(CheckInternetConnection.validateInternetConnection(activity)){
            activity.apply {
                startActivity(Intent(this, QuestionsActivity::class.java))
                overridePendingTransition(R.anim.left_in, R.anim.left_out)
                finish()
            }
        }else{
            ShowDialog.dialogShow(getString(R.string.text_error_conexion_internet), activity)
        }

    }


}