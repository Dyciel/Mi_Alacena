package cl.dycier.mialacena

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {

    lateinit var exitButton: Button
    lateinit var inventarioButton: Button
    lateinit var listShopButton: Button
    lateinit var shopBtn: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        exitButton=findViewById(R.id.exitBoton)
        inventarioButton=findViewById(R.id.inventarioBoton)
        listShopButton=findViewById(R.id.listaBoton)
        shopBtn=findViewById(R.id.tiendaBoton)


        inventarioButton.setOnClickListener{
            val intent = Intent(this@MainActivity, AddInventario::class.java)
            startActivity(intent)
            finish()
        }

        listShopButton.setOnClickListener{
            val intent = Intent(this@MainActivity, ListaCompras::class.java)
            startActivity(intent)
            finish()
        }

        shopBtn.setOnClickListener{
            val intent = Intent(this@MainActivity, Tienda::class.java)
            startActivity(intent)
            finish()
        }

        exitButton.setOnClickListener{
            val intent = Intent(this@MainActivity, Login::class.java)
            startActivity(intent)
            finish()
        }


    }
}