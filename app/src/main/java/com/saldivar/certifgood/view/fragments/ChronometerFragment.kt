package com.saldivar.certifgood.view.fragments

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.saldivar.certifgood.R
import com.saldivar.certifgood.view.activitys.CertificationsActivity
import com.saldivar.certifgood.view.activitys.QuestionsActivity
import kotlinx.android.synthetic.main.fragment_chronometer.*
import java.text.SimpleDateFormat
import java.util.*


class ChronometerFragment : Fragment() {
    private lateinit var cronometroView: CountDownTimer

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val rootview = inflater.inflate(R.layout.fragment_chronometer, container, false)
        onChronometer()
        return rootview
    }

    companion object {
        fun newInstance(): ChronometerFragment = ChronometerFragment()
    }

    private fun onChronometer() {
        val format=SimpleDateFormat("hh:mm:ss", Locale.getDefault())
        val tiempoExamen ="00:01:00"
        val time = format.parse(tiempoExamen)!!.time
        cronometroView = object : CountDownTimer(time,1000){
            override fun onTick(millisUntilFinished: Long) {
                var diff = millisUntilFinished
                val secondsInMilli: Long = 1000
                val minutesInMilli = secondsInMilli * 60
                val hoursInMilli = minutesInMilli * 60

                val elapsedHours = diff / hoursInMilli
                diff %= hoursInMilli

                val elapsedMinutes = diff / minutesInMilli
                diff %= minutesInMilli

                val elapsedSeconds = diff / secondsInMilli

                if (elapsedHours>0){
                    chronometerText.text = "$elapsedHours:$elapsedMinutes:$elapsedSeconds"
                }else{
                    chronometerText.text = "$elapsedMinutes:$elapsedSeconds"
                }
            }

            override fun onFinish() {
                backActivity()
            }

        }
    }

    private fun backActivity() {
        QuestionsActivity().apply {
            startActivity(Intent(this, CertificationsActivity::class.java))
            overridePendingTransition(R.anim.right_in, R.anim.right_out)
            finish()
        }
    }


}