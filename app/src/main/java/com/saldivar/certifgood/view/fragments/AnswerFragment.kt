package com.saldivar.certifgood.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.saldivar.certifgood.R
import com.saldivar.certifgood.utils.*
import com.saldivar.certifgood.viewModel.MainViewModel
import com.saldivar.permisolibrary.preferencesSaldivar
import kotlinx.android.synthetic.main.fragment_answer.view.*
import kotlinx.coroutines.*


class AnswerFragment : Fragment(),View.OnClickListener {
    private val viewModel by lazy{ ViewModelProvider(this).get(MainViewModel::class.java)}
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val rootview =inflater.inflate(R.layout.fragment_answer, container, false)
        rootview.buttonA.setOnClickListener(this)
        rootview.buttonB.setOnClickListener(this)
        rootview.buttonC.setOnClickListener(this)
        rootview.buttonD.setOnClickListener(this)
        return rootview
    }

    companion object {
        fun newInstance(): AnswerFragment = AnswerFragment()
    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.buttonA->{
                if(QuestionObject.respuesta_correcta=="respuesta1"){
                    QuestionObject.nota+=1
                    nextFragment()
                }
                else{
                    nextFragment()
                }
            }
            R.id.buttonB->{
                if(QuestionObject.respuesta_correcta=="respuesta2"){
                    QuestionObject.nota+=1
                    nextFragment()
                }
                else{
                    nextFragment()
                }
            }
            R.id.buttonC->{
                if(QuestionObject.respuesta_correcta=="respuesta3"){
                    QuestionObject.nota+=1
                    nextFragment()
                }
                else{
                    nextFragment()
                }
            }
            R.id.buttonD->{
                if(QuestionObject.respuesta_correcta=="respuesta4"){
                    QuestionObject.nota+=1
                    nextFragment()

                }
                else{
                    nextFragment()
                }
            }
        }
    }

    private fun nextFragment(){
        if (QuestionObject.listQuestionSize==1){
            SwitchFragment.detenerChronometer=1
            val nota = QuestionObject.nota
            val porcentajeAprobado = nota*100/CertificationObject.cantidadPreguntasEvaluar
            var notaString =""
            notaString = if(nota<10){
                "0$nota"
            }else{
                "$nota"
            }
            if(porcentajeAprobado>=CertificationObject.porcentajeAprobar){
                guardarDatos(notaString,porcentajeAprobado)
                ShowDialog.dialogShowCalificacionBuena("Su nota es $notaString con un porcentaje de $porcentajeAprobado% de respuestas correctas",
                    this.activity!!)
            }
            else{
                guardarDatos(notaString,porcentajeAprobado)
                ShowDialog.dialogShowCalificacionMala("Su nota es $notaString con un porcentaje de $porcentajeAprobado% de respuestas correctas",
                    this.activity!!)
            }
        }else{
            this.activity!!.supportFragmentManager.beginTransaction().apply{
                replace(R.id.containerQuestion,ShowQuestionsFragment.newInstance())
                setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                addToBackStack(null)
                commit()
        }
    }}

    private fun guardarDatos(notaString: String, porcentajeAprobado: Int) {
        val prefs = preferencesSaldivar(this.activity!!,0,"Datos_Usuario")
        val user = prefs.getString("usuario",CredentialsLogin.usuario)!!
        HistorialObject.estado_examen = porcentajeAprobado>=CertificationObject.porcentajeAprobar
        HistorialObject.nota_examen = notaString
        HistorialObject.porcentaje_examen = porcentajeAprobado.toString()
        HistorialObject.nombre_examen = CertificationObject.nombreCertificacion
        HistorialObject.usuario=user
        viewModel.sizeHistorial(user).observe(this.viewLifecycleOwner, Observer {size->
            viewModel.saveHistorial(size).observe(this.viewLifecycleOwner, Observer {})
        })
    }

}