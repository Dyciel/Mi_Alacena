package cl.dycier.mialacena

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class Inventario : AppCompatActivity() {

    lateinit var buttonBack: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inventario)

        buttonBack=findViewById(R.id.backButton)

        buttonBack.setOnClickListener{
            val intent = Intent(this@Inventario, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}