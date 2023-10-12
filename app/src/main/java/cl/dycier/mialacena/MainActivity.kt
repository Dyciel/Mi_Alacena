package cl.dycier.mialacena

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {

    lateinit var exitButton: Button
    lateinit var inventarioButton: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        exitButton=findViewById(R.id.exitBoton)
        inventarioButton=findViewById(R.id.inventarioBoton)


        inventarioButton.setOnClickListener{
            val intent = Intent(this@MainActivity, Inventario::class.java)
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