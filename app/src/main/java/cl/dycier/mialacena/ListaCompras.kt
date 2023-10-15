package cl.dycier.mialacena

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class ListaCompras : AppCompatActivity() {

    lateinit var buttonBack: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_compras)




        buttonBack=findViewById(R.id.backButton2)

        buttonBack.setOnClickListener{
            val intent = Intent(this@ListaCompras, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}