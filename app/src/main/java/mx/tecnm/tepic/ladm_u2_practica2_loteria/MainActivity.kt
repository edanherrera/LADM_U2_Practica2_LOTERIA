package mx.tecnm.tepic.ladm_u2_practica2_loteria

import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import mx.tecnm.tepic.ladm_u2_practica2_loteria.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    var cartas = arrayOf(R.drawable.carta1,R.drawable.carta2,R.drawable.carta3,R.drawable.carta4,R.drawable.carta5,R.drawable.carta6,R.drawable.carta7,
        R.drawable.carta8,R.drawable.carta9,R.drawable.carta10,R.drawable.carta11,R.drawable.carta12,R.drawable.carta13,R.drawable.carta14,R.drawable.carta15,
        R.drawable.carta16,R.drawable.carta17,R.drawable.carta18,R.drawable.carta19,R.drawable.carta20,R.drawable.carta21,R.drawable.carta22,R.drawable.carta23,
        R.drawable.carta24,R.drawable.carta25,R.drawable.carta26,R.drawable.carta27,R.drawable.carta28,R.drawable.carta29,R.drawable.carta30,R.drawable.carta31,
        R.drawable.carta32,R.drawable.carta33,R.drawable.carta34,R.drawable.carta35,R.drawable.carta36,R.drawable.carta37,R.drawable.carta38,R.drawable.carta39,
        R.drawable.carta40,R.drawable.carta41,R.drawable.carta42,R.drawable.carta43,R.drawable.carta44,R.drawable.carta45,R.drawable.carta46,R.drawable.carta47,
        R.drawable.carta48,R.drawable.carta49,R.drawable.carta50,R.drawable.carta51,R.drawable.carta52,R.drawable.carta53,R.drawable.carta54)

    var audio = arrayOf(R.raw.cart1,R.raw.cart2,R.raw.cart3,R.raw.cart4,R.raw.cart5,R.raw.cart6,R.raw.cart7,
        R.raw.cart8,R.raw.cart9,R.raw.cart10,R.raw.cart11,R.raw.cart12,R.raw.cart13,R.raw.cart14,R.raw.cart15,
        R.raw.cart16,R.raw.cart17,R.raw.cart18,R.raw.cart19,R.raw.cart20,R.raw.cart21,R.raw.cart22,R.raw.cart23,
        R.raw.cart24,R.raw.cart25,R.raw.cart26,R.raw.cart27,R.raw.cart28,R.raw.cart29,R.raw.cart30,R.raw.cart31,
        R.raw.cart32,R.raw.cart33,R.raw.cart34,R.raw.cart35,R.raw.cart36,R.raw.cart37,R.raw.cart38,R.raw.cart39,
        R.raw.cart40,R.raw.cart41,R.raw.cart42,R.raw.cart43,R.raw.cart44,R.raw.cart45,R.raw.cart46,R.raw.cart47,
        R.raw.cart48,R.raw.cart49,R.raw.cart50,R.raw.cart51,R.raw.cart52,R.raw.cart53,R.raw.cart54)
    var co = 0
    var resto = true
    lateinit var  hilo : hilo
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnPausar.visibility = View.GONE
        binding.btnResto.visibility = View.GONE

        val cartitas = Array<Cartas>(54,{Cartas(R.drawable.carta1,binding.carta,this,R.raw.loteria1)})

        corrutina(cartitas,this).start()
        hilo = hilo(cartitas,this)

        binding.btnIniciar.setOnClickListener {
            if (co>0){
                hilo.terminarHilo()
                hilo = hilo(cartitas,this)
                hilo.start()
            }else{
                hilo.start()
                co++
            }
            //corrutina(cartitas,this).start()
            binding.btnPausar.visibility = View.VISIBLE
            binding.btnIniciar.visibility = View.GONE
        }

        binding.btnPausar.setOnClickListener {
            hilo.pausarHilo()
            binding.btnResto.visibility = View.VISIBLE
            binding.btnIniciar.setText("Reiniciar")
            binding.btnIniciar.visibility = View.VISIBLE
        }
        binding.btnResto.setOnClickListener {
            val mp2 = MediaPlayer.create(this, R.raw.restantes)
            mp2.start()
            resto=false
            hilo.despausarHilo()
        }
    }

    fun corrutina(arr:Array<Cartas>,este:MainActivity) = GlobalScope.launch{
        var este = este
        var arr = arr
        for (i in 0..arr.size-1){
            arr[i].audio=este.audio[i]
            arr[i].numero=este.cartas[i]
        }
    }
}



class hilo(arr:Array<Cartas>,este:MainActivity):Thread(){
    var este = este
    var arr = arr
    private var ejecutar = true
    private var pausar = false
    override fun run() {
        super.run()
        var cont =0
        var cont2 =0
        arr.shuffle()
        while(ejecutar){
            if(!pausar){
                // SI NO (!) PAUSADO
                if (cont == arr.size-1){
                    //cont =0;
                    //cont2 =0;
                    terminarHilo()
                }
                if(!este.resto){
                    este.resto =true
                    sleep(2000)
                }

                este.runOnUiThread {
                    arr[cont++].rAudio()
                    arr[cont2++].mImg()
                    println("va en la carta: "+cont)
                }

            }
            sleep(2000)
        }
    }
    fun terminarHilo(){
        ejecutar = false
    }
    fun pausarHilo(){
        pausar = true
    }
    fun despausarHilo(){
        pausar = false
    }
    fun estaPausado() : Boolean {
        return pausar
    }
}
