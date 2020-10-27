package com.saldivar.certifgood.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentTransaction
import com.saldivar.certifgood.R
import com.saldivar.certifgood.utils.CertificationObject
import com.saldivar.certifgood.utils.QuestionObject
import com.saldivar.certifgood.utils.ShowDialog
import com.saldivar.certifgood.utils.SwitchFragment
import kotlinx.android.synthetic.main.fragment_answer.view.*


class AnswerFragment : Fragment(),View.OnClickListener {

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
                ShowDialog.dialogShowCalificacionBuena("Su nota es $notaString con un porcentaje de $porcentajeAprobado% de respuestas correctas",
                    this.activity!!)
            }
            else{
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
}