package com.saldivar.certifgood.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentTransaction
import com.saldivar.certifgood.R
import com.saldivar.certifgood.utils.QuestionObject
import com.saldivar.certifgood.utils.ShowDialog
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_answer.*


class AnswerFragment : Fragment(),View.OnClickListener {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val rootview =inflater.inflate(R.layout.fragment_answer, container, false)
        ui()
        return rootview
    }

    private fun ui() {
        buttonA.setOnClickListener(this)
        buttonB.setOnClickListener(this)
        buttonC.setOnClickListener(this)
        buttonD.setOnClickListener(this)
    }

    companion object {
        fun newInstance(): AnswerFragment = AnswerFragment()
    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.buttonA->{
                if(QuestionObject.respuesta_correcta=="respuesta1"){
                    nextFragment()
                    QuestionObject.nota+=1
                }
                else{
                    nextFragment()
                }
            }
            R.id.buttonB->{
                if(QuestionObject.respuesta_correcta=="respuesta2"){
                    nextFragment()
                    QuestionObject.nota+=1
                }
                else{
                    nextFragment()
                }
            }
            R.id.buttonC->{
                if(QuestionObject.respuesta_correcta=="respuesta3"){
                    nextFragment()
                    QuestionObject.nota+=1
                }
                else{
                    nextFragment()
                }
            }
            R.id.buttonD->{
                if(QuestionObject.respuesta_correcta=="respuesta4"){
                    nextFragment()
                    QuestionObject.nota+=1
                }
                else{
                    nextFragment()
                }
            }
        }
    }

    private fun nextFragment(){
        if (QuestionObject.listQuestionSize==1){
            ShowDialog.dialogShow("Su nota es ${QuestionObject.nota}",this.activity!!)
        }
        this.activity!!.supportFragmentManager.beginTransaction().apply{
            replace(R.id.containerQuestion,LevelsFragment.newInstance())
            setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            addToBackStack(null)
            commit()
    }}
}