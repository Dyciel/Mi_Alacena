package cl.dycier.mialacena

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import androidx.appcompat.app.AlertDialog

class AddListas : AppCompatActivity() {
    lateinit var buttonBack: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_listas)

        val listP = findViewById<ListView>(R.id.listElementos)
        val btnAdd = findViewById<Button>(R.id.buttonAñadir)
        val arrayAdapter: ArrayAdapter<*>
        val nomProd = findViewById<EditText>(R.id.nameProducto)
        val cantProd = findViewById<EditText>(R.id.cantProducto)

        val itemsProd = mutableListOf("Pan", "Arroz", "Fideos")


        arrayAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, itemsProd)

        listP.adapter = arrayAdapter

        btnAdd.setOnClickListener {
            itemsProd.add(nomProd.text.toString())
            arrayAdapter.notifyDataSetChanged()
        }

        listP.setOnItemClickListener { adapterView, view, posicion, l ->

            val objetos = itemsProd[posicion]

            AlertDialog.Builder(this).apply{
                setTitle(nomProd.text.toString())
                setMessage(cantProd.text.toString())
                setPositiveButton("Atrás", null)

            }.show()
        }

        buttonBack=findViewById(R.id.backButton5)

        buttonBack.setOnClickListener{
            val intent = Intent(this@AddListas, ListaCompras::class.java)
            startActivity(intent)
            finish()
        }
    }
}