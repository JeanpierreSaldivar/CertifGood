package com.saldivar.certifgood.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.saldivar.certifgood.R
import com.saldivar.certifgood.repo.objetos.Question
import com.saldivar.certifgood.utils.QuestionObject
import com.saldivar.certifgood.viewModel.MainViewModel
import kotlinx.android.synthetic.main.fragment_show_questions.*


class ShowQuestionsFragment : Fragment() {
    private val viewModel by lazy{ ViewModelProvider(this).get(MainViewModel::class.java)}

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val rootview = inflater.inflate(R.layout.fragment_show_questions, container, false)
        queryListQuestions()
        return rootview
    }

    private fun queryListQuestions() {
        QuestionObject.listQuestionSize -= 1
        val result = QuestionObject.listQuestion!![QuestionObject.listQuestionSize-1]
        val valueList = result.toString()
        viewModel.getQuestionsList(valueList).observe(this.viewLifecycleOwner, Observer {
            val list:MutableList<Question> = it
            QuestionObject.contador_pregunta+=1
            val numero = QuestionObject.contador_pregunta
            textPregunta.text = "$numero. ${list[0].pregunta}"
            textRespuesta1.text ="a. ${list[0].respuesta1}"
            textRespuesta2.text ="b. ${list[0].respuesta2}"
            textRespuesta3.text ="c. ${list[0].respuesta3}"
            textRespuesta4.text ="d. ${list[0].respuesta4}"
            QuestionObject.respuesta_correcta = list[0].respuesta_correcta
        })
    }

    companion object {
        fun newInstance(): ShowQuestionsFragment = ShowQuestionsFragment()
    }

}