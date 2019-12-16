package com.example.appkotlin

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.drawable.Icon
import android.os.Build
import android.os.Bundle
import android.speech.RecognizerIntent
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.google.android.material.floatingactionbutton.FloatingActionButton

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity() {
    var grabar: TextView? = null
    private val RECOGNIZE_SPEECH_ACTIVITY = 1
    private var rnd=0
    private var face=0;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        var tvPregunta = findViewById<TextView>(R.id.tvPregunta)
        var tvRespuesta = findViewById<TextView>(R.id.tvRespuesta)
        //var fbcara=findViewById<FloatingActionButton>(R.id.fbFace)
        fbMicro.setOnClickListener { view ->
            hablar(view)
        }
        fbLike.setOnClickListener { view ->
            face++
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    cambiarcara(view)
                }

        }
        fbDislike.setOnClickListener { view ->
            face--
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    cambiarcara(view)
                }

        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int,
                                  data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            RECOGNIZE_SPEECH_ACTIVITY ->
                if (resultCode == Activity.RESULT_OK && null != data) {
                    val speech = data
                        .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
                    val strSpeech2Text = speech[0]
                    rnd= (Math.random()*3).toInt()

                    tvPregunta.setText("¿"+strSpeech2Text.toUpperCase()+"?")
                    if(rnd==2){
                        tvRespuesta.setText("No es probable")
                    }
                    if(rnd==1){
                        tvRespuesta.setText("Es probable")
                    }
                    if(rnd==0){
                        tvRespuesta.setText("No estoy seguro, consulta con alguien cercano")
                    }
                }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    fun hablar(v: View) {
        val intentActionRecognizeSpeech = Intent(
            RecognizerIntent.ACTION_RECOGNIZE_SPEECH)

        intentActionRecognizeSpeech.putExtra(
            RecognizerIntent.EXTRA_LANGUAGE_MODEL, "es-ES")
        try {
            startActivityForResult(intentActionRecognizeSpeech,
                RECOGNIZE_SPEECH_ACTIVITY)
        } catch (a: ActivityNotFoundException) {
            Toast.makeText(applicationContext,
                "Tú dispositivo no soporta el reconocimiento por voz",
                Toast.LENGTH_SHORT).show()
        }
    }
    @RequiresApi(Build.VERSION_CODES.M)
    fun cambiarcara(v:View){
        if(face<0){
            imgFace.setImageResource(R.drawable.sadface)
        }
        if(face==0){
            imgFace.setImageResource(R.drawable.smilingface)
        }
        if(face>0){
            imgFace.setImageResource(R.drawable.happyface)
        }
    }
    //<div>Icons made by <a href="https://www.flaticon.com/authors/dave-gandy" title="Dave Gandy">Dave Gandy</a> from <a href="https://www.flaticon.com/" title="Flaticon">www.flaticon.com</a></div>
    //<div>Icons made by <a href="https://www.flaticon.com/authors/freepik" title="Freepik">Freepik</a> from <a href="https://www.flaticon.com/" title="Flaticon">www.flaticon.com</a></div>
    //<div>Icons made by <a href="https://www.flaticon.com/authors/freepik" title="Freepik">Freepik</a> from <a href="https://www.flaticon.com/" title="Flaticon">www.flaticon.com</a></div>
    //<div>Icons made by <a href="https://www.flaticon.com/authors/freepik" title="Freepik">Freepik</a> from <a href="https://www.flaticon.com/" title="Flaticon">www.flaticon.com</a></div>
    //<div>Icons made by <a href="https://www.flaticon.com/authors/freepik" title="Freepik">Freepik</a> from <a href="https://www.flaticon.com/" title="Flaticon">www.flaticon.com</a></div>
}
