package cl.dycier.mialacena

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class ListaCompras : AppCompatActivity() {

    lateinit var buttonBack: Button
    lateinit var addlistcompra: Button
    lateinit var ingcompra: Button
    lateinit var histocompra: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_compras)


        addlistcompra=findViewById(R.id.addListButton)

        addlistcompra.setOnClickListener{
            val intent = Intent(this@ListaCompras, AddListas::class.java)
            startActivity(intent)
            finish()
        }

        ingcompra=findViewById(R.id.ingListShopButton3)

        ingcompra.setOnClickListener{
            val intent = Intent(this@ListaCompras, IngresoCompras::class.java)
            startActivity(intent)
            finish()
        }

        histocompra=findViewById(R.id.hisListButton)

        histocompra.setOnClickListener{
            val intent = Intent(this@ListaCompras, HitorialCompras::class.java)
            startActivity(intent)
            finish()
        }

        buttonBack=findViewById(R.id.backButton2)

        buttonBack.setOnClickListener{
            val intent = Intent(this@ListaCompras, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}