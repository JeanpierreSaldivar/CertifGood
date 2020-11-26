package com.saldivar.certifgood.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import com.saldivar.certifgood.R
import com.saldivar.certifgood.utils.*
import kotlinx.android.synthetic.main.fragment_answer.view.*


class AnswerFragment : Fragment(),View.OnClickListener{
    private val viewModel by lazy{ this.viewModel()}
    private val prefs by lazy { this.activity!!.preferences() }
    private val user = prefs.getString(getString(R.string.email_User),getString(R.string.email_User))!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
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
                validarRespuesta(respuesta = "respuesta1")
                nextFragment()
            }
            R.id.buttonB->{
                validarRespuesta(respuesta = "respuesta2")
                nextFragment()
            }
            R.id.buttonC->{
                validarRespuesta(respuesta = "respuesta3")
                nextFragment()
            }
            R.id.buttonD->{
                validarRespuesta(respuesta = "respuesta4")
                nextFragment()
            }
        }
    }

    fun validarRespuesta(respuesta:String){
        if(QuestionObject.respuesta_correcta==respuesta){
            QuestionObject.nota+=1
        }
    }

    private fun nextFragment(){
        if (QuestionObject.listQuestionSize==1){
            SwitchFragment.detenerChronometer=1
            val nota = QuestionObject.nota
            val porcentajeAprobado = nota*100/CertificationObject.cantidadPreguntasEvaluar
            val notaString = if(nota<10){
                "0$nota"
            }else{
                "$nota"
            }
            guardarDatos(notaString,porcentajeAprobado)
            if(porcentajeAprobado>=CertificationObject.porcentajeAprobar){
                guardarDatosFirebase()
                ShowDialog.dialogShowCalificacionBuena("Su nota es $notaString con un porcentaje de $porcentajeAprobado% de respuestas correctas",
                    this.activity!!)
            }
            else{
                guardarDatosFirebase()
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
        HistorialObject.apply {
            estado_examen = porcentajeAprobado>=CertificationObject.porcentajeAprobar
            nota_examen = notaString
            porcentaje_examen = porcentajeAprobado.toString()
            nombre_examen = CertificationObject.nombreCertificacion
            usuario=user
        }

    }

    private fun guardarDatosFirebase(){
        viewModel.sizeHistorial(user).observe(this.viewLifecycleOwner, Observer {size->
            viewModel.saveHistorial(size).observe(this.viewLifecycleOwner, Observer {})
        })
    }

}