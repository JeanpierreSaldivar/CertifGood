package com.saldivar.certifgood.view.activitys

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.FragmentTransaction
import com.saldivar.certifgood.R
import com.saldivar.certifgood.utils.CertificationObject
import com.saldivar.certifgood.utils.QuestionObject
import com.saldivar.certifgood.utils.listNumberRandom
import com.saldivar.certifgood.view.fragments.AnswerFragment
import com.saldivar.certifgood.view.fragments.ChronometerFragment
import com.saldivar.certifgood.view.fragments.ListCertificationsFragment
import com.saldivar.certifgood.view.fragments.ShowQuestionsFragment

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
        val lowerRank = 0
        val topRank = CertificationObject.cantidadPreguntas+1
        val listSelectionQuestions = mutableListOf<Int>()
        val numberQuestionsEvaluate = CertificationObject.cantidadPreguntasEvaluar
        for(i in 1..numberQuestionsEvaluate step 1){
            val numberRandom = listNumberRandom(lowerRank..topRank)
            if(listSelectionQuestions.size>0){
                listSelectionQuestions.filter { numberRandom!=it }.forEach{
                    listSelectionQuestions.add(it)
                }
            }else{
                listSelectionQuestions.add(numberRandom)
            }
        }
        QuestionObject.listQuestion = listSelectionQuestions
        QuestionObject.listQuestionSize = listSelectionQuestions.size+1
        val ss = 2
    }

    private fun openFragmentChronometer(fragment: ChronometerFragment){
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

    internal fun backActivity(context: Context){
        context.apply {
            startActivity(Intent(this, CertificationsActivity::class.java))
            this@QuestionsActivity.overridePendingTransition(R.anim.right_in, R.anim.right_out)
            this@QuestionsActivity.finish()
        }
    }
}
