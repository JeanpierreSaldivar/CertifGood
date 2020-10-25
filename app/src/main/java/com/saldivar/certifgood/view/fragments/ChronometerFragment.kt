package com.saldivar.certifgood.view.fragments

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.saldivar.certifgood.R
import com.saldivar.certifgood.utils.CertificationObject
import com.saldivar.certifgood.utils.ShowDialog
import com.saldivar.certifgood.utils.SwitchFragment
import com.saldivar.certifgood.view.activitys.CertificationsActivity
import com.saldivar.certifgood.view.activitys.QuestionsActivity
import kotlinx.android.synthetic.main.fragment_chronometer.*
import kotlinx.android.synthetic.main.fragment_chronometer.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*


class ChronometerFragment : Fragment() {
    private lateinit var cronometroView:CountDownTimer
    private  lateinit var chronometerTextView :TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val rootview = inflater.inflate(R.layout.fragment_chronometer, container, false)
        chronometerTextView = rootview.chronometerText as TextView
        onChronometer()
        return rootview
    }

    companion object {
        fun newInstance(): ChronometerFragment = ChronometerFragment()
    }

    private fun onChronometer() {
            val tiempoExamen = CertificationObject.tiempoPrueba
            val time = tiempoExamen.toLong()
            cronometroView = object : CountDownTimer(time,1000){
                override fun onTick(millisUntilFinished: Long) {
                    var diff = millisUntilFinished
                    val secondsInMilli: Long = 1000
                    val minutesInMilli = secondsInMilli * 60
                    val hoursInMilli = minutesInMilli * 60
                    val daysInMilli = hoursInMilli * 24

                    val elapsedDays = diff / daysInMilli
                    diff %= daysInMilli

                    val elapsedHours = diff / hoursInMilli
                    diff %= hoursInMilli

                    val elapsedMinutes = diff / minutesInMilli
                    diff %= minutesInMilli

                    val elapsedSeconds = diff / secondsInMilli
                    if(SwitchFragment.detenerChronometer==0){
                        when{
                            elapsedHours>0.toLong()->chronometerTextView.text = "$elapsedHours:$elapsedMinutes:$elapsedSeconds"
                            elapsedMinutes<10.toLong() && elapsedSeconds<10.toLong() -> chronometerTextView.text = "0$elapsedMinutes:0$elapsedSeconds"
                            elapsedMinutes<10.toLong()->chronometerTextView.text = "0$elapsedMinutes:$elapsedSeconds"
                            elapsedMinutes>=10.toLong()->chronometerTextView.text = "$elapsedMinutes:$elapsedSeconds"
                            elapsedSeconds<10.toLong()->chronometerTextView.text = "$elapsedMinutes:0$elapsedSeconds"
                            elapsedSeconds>=10.toLong()->chronometerTextView.text = "$elapsedMinutes:$elapsedSeconds"

                        }
                    }
                }

                override fun onFinish() {
                    mostrarDialogo()
                }

            }.start()


    }

    private fun mostrarDialogo() {
        SwitchFragment.detenerChronometer=1
            ShowDialog.dialogShow("Lo sentimos, el tiempo de la prueba a culminado",this.activity!!)
    }




}