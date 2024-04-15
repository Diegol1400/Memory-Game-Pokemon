package com.example.memorama

import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.delay
import java.util.*

var img01: ImageButton? = null
var img02:ImageButton? = null
var img03:ImageButton? = null
var img04:ImageButton? = null
var img11:ImageButton? = null
var img12:ImageButton? = null
var img13:ImageButton? = null
var img14:ImageButton? = null
var img21:ImageButton? = null
var img22:ImageButton? = null
var img23:ImageButton? = null
var img24:ImageButton? = null
var img31:ImageButton? = null
var img32:ImageButton? = null
var img33:ImageButton? = null
var img34:ImageButton? = null
var tablero = arrayOfNulls<ImageButton>(16)
var tvPuntuacion: TextView? = null
var tvErrores: TextView? = null
var edittext: EditText? = null
var puntos = 0
var aciertos = 0
var oportunidades = 0
var intentos = 0

//imagenes
lateinit var imagenes: IntArray
var fondo = 0

//variables del juego
var arrayDesordenado: ArrayList<Int>? = null
var primero: ImageButton? = null
var numeroPrimero:Int = 0
var numeroSegundo:Int = 0
var bloqueo = false
val handler = Handler()


//val Spikachu = MediaPlayer.create(this, R.raw.sonidopikachu)
var musica =MediaPlayer()
var musicapikachu =MediaPlayer()
var musicaraichu =MediaPlayer()
var musicabulbasaur =MediaPlayer()
var musicavenasaur =MediaPlayer()
var musicasquirtle =MediaPlayer()
var musicablastoise =MediaPlayer()
var musicacharmander =MediaPlayer()
var musicacharizard =MediaPlayer()
var musicavictoria =MediaPlayer()
var cartas =MediaPlayer()

class ModoDesafio : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_modo_desafio)
        val bundle= intent.extras
        val dato = bundle?.getString("Dato")
        val Datousuario = findViewById<TextView>(R.id.usuario2)
        Datousuario.setText("Usuario: $dato")
        musica = MediaPlayer.create(this, R.raw.musicadesafio)
        musicapikachu = MediaPlayer.create(this, R.raw.sonidopikachu)
        musicaraichu = MediaPlayer.create(this, R.raw.sonidoraichu)
        musicabulbasaur = MediaPlayer.create(this, R.raw.sonidobulbasaur)
        musicavenasaur = MediaPlayer.create(this, R.raw.sonidovenasaur)
        musicasquirtle = MediaPlayer.create(this, R.raw.sonidosquirtle)
        musicablastoise = MediaPlayer.create(this, R.raw.sonidoblaistose)
        musicacharmander = MediaPlayer.create(this, R.raw.sonidocharmander)
        musicacharizard= MediaPlayer.create(this, R.raw.sonidocharizard)
        musicavictoria = MediaPlayer.create(this, R.raw.musicavictoria)
        cartas = MediaPlayer.create(this, R.raw.cartas)
        musica.start()
        init()

    }

    private fun cargarTablero() {
        img01 = findViewById<ImageButton>(R.id.boton01)
        img02 = findViewById<ImageButton>(R.id.boton02)
        img03 = findViewById<ImageButton>(R.id.boton03)
        img04 = findViewById<ImageButton>(R.id.boton04)
        img11 = findViewById<ImageButton>(R.id.boton11)
        img12 = findViewById<ImageButton>(R.id.boton12)
        img13 = findViewById<ImageButton>(R.id.boton13)
        img14 = findViewById<ImageButton>(R.id.boton14)
        img21 = findViewById<ImageButton>(R.id.boton21)
        img22 = findViewById<ImageButton>(R.id.boton22)
        img23 = findViewById<ImageButton>(R.id.boton23)
        img24 = findViewById<ImageButton>(R.id.boton24)
        img31 = findViewById<ImageButton>(R.id.boton31)
        img32 = findViewById<ImageButton>(R.id.boton32)
        img33 = findViewById<ImageButton>(R.id.boton33)
        img34 = findViewById<ImageButton>(R.id.boton34)
        tablero[0] = img01
        tablero[1] = img02
        tablero[2] = img03
        tablero[3] = img04
        tablero[4] = img11
        tablero[5] = img12
        tablero[6] = img13
        tablero[7] = img14
        tablero[8] = img21
        tablero[9] = img22
        tablero[10] = img23
        tablero[11] = img24
        tablero[12] = img31
        tablero[13] = img32
        tablero[14] = img33
        tablero[15] = img34
    }
    fun Reiniciar(view: View) {
        init()
        musica.start()
        //  mediaPlayer.stop()
    }
    fun Salir(view: View) {
        musica.stop()
        var prueba1 = Intent(application, MainActivity::class.java)
        startActivity((prueba1))
        finish()

    }


    private fun cargarTexto() {
        tvPuntuacion = findViewById<TextView>(R.id.puntuacion)
        tvErrores = findViewById<TextView>(R.id.errores)
        puntos = 0
        aciertos = 0
        val builder = AlertDialog.Builder(this)
        val inflater = layoutInflater
        val dialogLayout = inflater.inflate(R.layout.edit_text_layout, null)
        val editText = dialogLayout.findViewById<EditText>(R.id.Intentos)


        with(builder){
            setTitle("Bienvenido al modo desafio!!!")
            setMessage("¿Cuantos intentos quieres?")
            setCancelable(false)
            setPositiveButton("Ok"){dialog, which ->
                if (editText.text.isEmpty()) {
                    android.widget.Toast.makeText(
                        applicationContext,
                        "ESCRIBE UN NUMERO",
                        android.widget.Toast.LENGTH_SHORT
                    ).show()
                    init()
                }
                else {
                    oportunidades = editText.text.toString().toInt()
                }

            }
            setView(dialogLayout)
            show()
        }

    }

    private fun cargarImagenes() {
        imagenes = intArrayOf(
            R.drawable.pikachu,
            R.drawable.raichu2,
            R.drawable.charmander2,
            R.drawable.charizard2,
            R.drawable.squirtle2,
            R.drawable.blastoise2,
            R.drawable.bulbasaur2,
            R.drawable.venasaur2
        )
        fondo = R.drawable.carta
    }

    private fun barajar(longitud: Int): ArrayList<Int>? {
        val result = ArrayList<Int>()
        for (i in 0 until longitud * 2) {
            result.add(i % longitud)
        }
        Collections.shuffle(result)
        System.out.println(Arrays.toString(result.toArray()))
        return result
    }

    private fun comprobar(i: Int, imgb: ImageButton) {

        if (primero == null) {
            primero = imgb
            primero!!.scaleType = ImageView.ScaleType.CENTER_CROP
            primero!!.setImageResource(imagenes[arrayDesordenado!![i]])
            primero!!.isEnabled = false
            numeroPrimero = arrayDesordenado!![i]
            // System.out.println(numeroPrimero)
        } else {
            bloqueo = true
            imgb.scaleType = ImageView.ScaleType.CENTER_CROP
            imgb.setImageResource(imagenes[arrayDesordenado!![i]])
            imgb.isEnabled = false
            numeroSegundo = arrayDesordenado!![i]
            if (numeroPrimero == numeroSegundo) {
                if(numeroPrimero==0 || numeroSegundo==0){
                    musica.pause()
                    Handler().postDelayed({
                        musicapikachu.start()
                    },500)
                    Handler().postDelayed({
                        musica.start()
                    },4000)
                }
                else if(numeroPrimero==1 || numeroSegundo==1){
                    musica.pause()
                    Handler().postDelayed({
                        musicaraichu.start()
                    },500)
                    Handler().postDelayed({
                        musica.start()
                    },4000)
                }
                else if(numeroPrimero==2 || numeroSegundo==2){
                    musica.pause()
                    Handler().postDelayed({
                        musicacharmander.start()
                    },500)
                    Handler().postDelayed({
                        musica.start()
                    },4000)
                }
                else if(numeroPrimero==3 || numeroSegundo==3){
                    musica.pause()
                    Handler().postDelayed({
                        musicacharizard.start()
                    },500)
                    Handler().postDelayed({
                        musica.start()
                    },4000)
                }
                else if(numeroPrimero==4 || numeroSegundo==4){
                    musica.pause()
                    Handler().postDelayed({
                        musicasquirtle.start()
                    },500)
                    Handler().postDelayed({
                        musica.start()
                    },4000)
                }
                else if(numeroPrimero==5 || numeroSegundo==5){
                    musica.pause()
                    Handler().postDelayed({
                        musicablastoise.start()
                    },500)
                    Handler().postDelayed({
                        musica.start()
                    },4000)
                }
                else if(numeroPrimero==6 || numeroSegundo==6){
                    musica.pause()
                    Handler().postDelayed({
                        musicabulbasaur.start()
                    },500)
                    Handler().postDelayed({
                        musica.start()
                    },4000)
                }
                else if(numeroPrimero==7 || numeroSegundo==7){
                    musica.pause()
                    Handler().postDelayed({
                        musicavenasaur.start()
                    },500)
                    Handler().postDelayed({
                        musica.start()
                    },4000)
                }
                primero = null
                bloqueo = false
                aciertos++
                tvPuntuacion?.setText("Puntuación: $aciertos")
                if (aciertos == imagenes.size) {
                    musica.pause()
                    Handler().postDelayed({
                        musicavictoria.start()
                    },500)
                    Handler().postDelayed({
                        musica.start()
                    },4000)
                    val toast =
                        Toast.makeText(applicationContext, "Has ganado!!", Toast.LENGTH_LONG)
                    toast.show()
                    MaterialAlertDialogBuilder(this)
                        .setTitle("FELICIDADES HAS GANADO")
                        .setMessage("¿Quieres volver a jugar?")
                        .setCancelable(true)
                        .setNegativeButton("No"){ dialog, which -> finish()
                        }.setPositiveButton("si"){dialog, which -> init()
                        }.show()
                }
            } else {
                handler.postDelayed({
                    primero!!.scaleType = ImageView.ScaleType.CENTER_CROP
                    primero!!.setImageResource(fondo)
                    primero!!.isEnabled = true
                    imgb.scaleType = ImageView.ScaleType.CENTER_CROP
                    imgb.setImageResource(fondo)
                    imgb.isEnabled = true
                    bloqueo = false
                    primero = null
                    puntos++
                   // oportunidades--
                    tvErrores?.setText("Errores: $puntos")
                  //  tvIntentos?.setText("Intentos restantes: $oportunidades")
                    if(oportunidades==puntos){
                        MaterialAlertDialogBuilder(this)
                            .setTitle("HAS PERDIDO!!!")
                            .setMessage("¿Quieres volver a jugar?")
                            .setCancelable(true)
                            .setNegativeButton("No"){ dialog, which -> finish()
                            }.setPositiveButton("si"){dialog, which -> init()
                            }.show()
                    }
                }, 1000)
            }
        }


    }

    private fun init() {
        cargarTablero()
        cargarTexto()
        cargarImagenes()

        puntos=0
        aciertos=0
        tvErrores?.setText("Errores: $puntos")
        tvPuntuacion?.setText("Puntuación: $aciertos")
        arrayDesordenado = barajar(imagenes.size)
        for (i in tablero.indices) {
            tablero[i]!!.scaleType = ImageView.ScaleType.CENTER_CROP
            tablero[i]!!.setImageResource(imagenes[arrayDesordenado?.get(i)!!])
            //tablero[i].setImageResource(fondo);
        }
        handler.postDelayed({
            for (i in tablero.indices) {
                tablero[i]!!.scaleType = ImageView.ScaleType.CENTER_CROP
                //tablero[i].setImageResource(imagenes[arrayDesordenado.get(i)]);
                tablero[i]!!.setImageResource(fondo)
            }
        }, 5)
        for (i in tablero.indices) {
            tablero[i]!!.isEnabled = true
            tablero[i]!!.setOnClickListener { if (!bloqueo) comprobar(i, tablero[i]!!) }
        }
    }

}

