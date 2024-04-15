package com.example.memorama

import android.content.Intent
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.*

class MainActivity : AppCompatActivity() {

    var musica=MediaPlayer()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val Usuario = findViewById<EditText>(R.id.usuario)
        val Modofacil = findViewById<Button>(R.id.Modofacil)
        val Mododesafio = findViewById<Button>(R.id.desafio)
        val Activar= findViewById<RadioButton>(R.id.Activacion)
        val activacion= Activar.isChecked.toString()

        Mododesafio.isEnabled=true
        Modofacil.isEnabled=true

        init()
        Modofacil.setOnClickListener {
            if(Usuario.text.isEmpty()){
                Toast.makeText(
                    applicationContext,
                    "ESCRIBE UN USUARIO" , Toast.LENGTH_LONG).show()
            }
            else {
                var prueba1 = Intent(application, ModoFacil::class.java)
                prueba1.putExtra("Dato", Usuario.text.toString())
                musica.stop()
                startActivity((prueba1))
                finish()
            }
        }

        Mododesafio.setOnClickListener {
            if(Usuario.text.isEmpty()){
                Toast.makeText(
                    applicationContext,
                    "ESCRIBE UN USUARIO" , Toast.LENGTH_LONG).show()
            }
          else if(Activar.isChecked==false){
                Toast.makeText(
                    applicationContext,
                    "ACTIVA EL MODO DESAFIO" , Toast.LENGTH_LONG).show()

          }
          else {

                var prueba1 = Intent(application, ModoDesafio::class.java)
                prueba1.putExtra("Dato", Usuario.text.toString())
                musica.stop()
                startActivity((prueba1))
                finish()
            }


        }

    }


    fun Salir(view: View) {
        musica.pause()
        finish()
      //  mediaPlayer.stop()
    }
    private fun init() {
        musica = MediaPlayer.create(this, R.raw.musicafondo2)
        musica.start()
    }
}