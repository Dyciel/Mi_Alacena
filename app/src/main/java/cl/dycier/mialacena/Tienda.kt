package cl.dycier.mialacena

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class Tienda : AppCompatActivity() {

    lateinit var buttonBack: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tienda)




        buttonBack=findViewById(R.id.backButton3)

        buttonBack.setOnClickListener{
            val intent = Intent(this@Tienda, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}