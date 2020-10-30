package com.saldivar.certifgood.view.fragments
import android.os.Bundle
import android.os.CountDownTimer
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.saldivar.certifgood.R
import com.saldivar.certifgood.utils.*
import com.saldivar.certifgood.viewModel.MainViewModel
import com.saldivar.permisolibrary.preferencesSaldivar
import kotlinx.android.synthetic.main.fragment_chronometer.view.*


class ChronometerFragment : Fragment() {
    private val viewModel by lazy{ ViewModelProvider(this).get(MainViewModel::class.java)}
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
            ShowDialog.dialogShowCalificacionBuena("Lo sentimos, el tiempo de la prueba a culminado. Su nota es $notaString con un porcentaje de $porcentajeAprobado% de respuestas correctas",
                this.activity!!)
        }
        else{
            guardarDatos(notaString,porcentajeAprobado)
            ShowDialog.dialogShowCalificacionMala("Lo sentimos, el tiempo de la prueba a culminado. Su nota es $notaString con un porcentaje de $porcentajeAprobado% de respuestas correctas",
                this.activity!!)
        }
    }

    private fun guardarDatos(notaString: String, porcentajeAprobado: Int) {
        val prefs = preferencesSaldivar(this.activity!!,0,"Datos_Usuario")
        val user = prefs.getString("usuario",CredentialsLogin.usuario)!!
        HistorialObject.estado_examen = porcentajeAprobado>=CertificationObject.porcentajeAprobar
        HistorialObject.nota_examen = notaString
        HistorialObject.porcentaje_examen = porcentajeAprobado.toString()
        HistorialObject.nombre_examen = CertificationObject.nombreCertificacion
        HistorialObject.usuario=user
        viewModel.sizeHistorial(user).observe(this.viewLifecycleOwner, androidx.lifecycle.Observer {size->
            viewModel.saveHistorial(size).observe(this.viewLifecycleOwner, androidx.lifecycle.Observer {})
        })
    }


}