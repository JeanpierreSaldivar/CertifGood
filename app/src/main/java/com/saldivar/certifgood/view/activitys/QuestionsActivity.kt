package com.saldivar.certifgood.view.activitys

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.system.Os.remove
import android.util.Log
import android.widget.TextView
import androidx.fragment.app.FragmentTransaction
import com.saldivar.certifgood.R
import com.saldivar.certifgood.utils.*
import com.saldivar.certifgood.view.fragments.AnswerFragment
import com.saldivar.certifgood.view.fragments.ChronometerFragment
import com.saldivar.certifgood.view.fragments.ListCertificationsFragment
import com.saldivar.certifgood.view.fragments.ShowQuestionsFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class QuestionsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_preguntas)
        supportActionBar?.hide()
        generateRandomNumbers()
        openFragmentChronometer(ChronometerFragment.newInstance())
        openFragmentShowQuestion(ShowQuestionsFragment.newInstance())
        openFragmentAnswer(AnswerFragment.newInstance())
    }

    private fun generateRandomNumbers() {
        var contadorPreguntas = 0
        val lowerRank = 1
        val topRank = CertificationObject.cantidadPreguntas
        val listSelectionQuestions = mutableListOf<Int>()
        val numberQuestionsEvaluate = CertificationObject.cantidadPreguntasEvaluar
            while (contadorPreguntas<numberQuestionsEvaluate){
                contadorPreguntas+=1
                val numberRandom = listNumberRandom(lowerRank..topRank)
                Log.d("numeros_generados","$numberRandom")//demostracion
                if(contadorPreguntas==1){
                    listSelectionQuestions.add(numberRandom)
                }else{
                    val verificarValorRepetido =listSelectionQuestions.filter { it == numberRandom }
                    if (verificarValorRepetido.isNotEmpty()){
                        contadorPreguntas-=1
                    }
                    else{
                        Log.d("numeros_dentro","$numberRandom")//demostracion
                        listSelectionQuestions.add(numberRandom)
                    }
                }
            }
            QuestionObject.listQuestion = listSelectionQuestions
            QuestionObject.listQuestionSize = listSelectionQuestions.size+1
    }

    private fun openFragmentChronometer(fragment: ChronometerFragment){
        SwitchFragment.detenerChronometer=0
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.containerCronometro,fragment)
            setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            addToBackStack(null)
            commit()}
    }

    private fun openFragmentShowQuestion(fragment: ShowQuestionsFragment) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.containerQuestion,fragment)
            setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            addToBackStack(null)
            commit()}
    }

    private fun openFragmentAnswer(fragment: AnswerFragment) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.containerAnswer,fragment)
            setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            addToBackStack(null)
            commit()}
    }

    override fun onBackPressed() {
        SwitchFragment.detenerChronometer = 1
        ShowDialog.dialogShowOptions("¿Desea salir de la prueba?",this@QuestionsActivity)
    }
    internal fun backActivity(context: Context){
        context.apply {
            startActivity(Intent(this, CertificationsActivity::class.java))
            this@QuestionsActivity.overridePendingTransition(R.anim.right_in, R.anim.right_out)
            this@QuestionsActivity.finish()
        }
    }
}
