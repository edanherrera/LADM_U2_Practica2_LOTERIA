package mx.tecnm.tepic.ladm_u2_practica2_loteria

import android.media.MediaPlayer
import android.widget.ImageView

class Cartas(numero : Int, img:ImageView, este : MainActivity,audio : Int) {

    var numero = numero
    var img = img
    var este = este
    var audio = audio
    fun mImg(){
        este.runOnUiThread {
            img.setImageResource(numero)
        }
    }
    fun rAudio(){
        val mp = MediaPlayer.create(este, audio)
        mp.start()
    }
}