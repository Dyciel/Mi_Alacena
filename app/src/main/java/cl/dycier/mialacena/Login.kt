package cl.dycier.mialacena

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class Login : AppCompatActivity() {

    lateinit var userName: EditText
    lateinit var password: EditText
    lateinit var buttonLogin: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        userName=findViewById(R.id.userName)
        password=findViewById(R.id.password)
        buttonLogin=findViewById(R.id.buttonLogin)

        buttonLogin.setOnClickListener {

            if(userName.text.toString().length==0)
                Toast.makeText(this, "Ingrese nombre de usuario", Toast.LENGTH_SHORT).show()
            if(password.text.toString().length==0)
                Toast.makeText(this, "Ingrese contraseña", Toast.LENGTH_SHORT).show()
            if(password.text.toString().length < 5)
            {
                Toast.makeText(this, "Contraseña Inválida. Ingrese una contraseña válida (mínimo 5 carácteres.", Toast.LENGTH_SHORT).show()
            }
            else
            {
                val intent = Intent(this@Login, AddInventario::class.java)
                startActivity(intent)
                finish()
            }


        }


    }
}