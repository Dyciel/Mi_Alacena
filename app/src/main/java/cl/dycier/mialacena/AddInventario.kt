package cl.dycier.mialacena

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView

class AddInventario : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_inventario)

        val listProd = findViewById<ListView>(R.id.listaObjetos)
        val btnAdd = findViewById<Button>(R.id.addButton)
        val arrayAdapter:ArrayAdapter<*>
        val textProd = findViewById<EditText>(R.id.lista)

        val nomProd = mutableListOf("Pan", "Arroz", "Fideos")



        arrayAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, nomProd)

        listProd.adapter = arrayAdapter

        btnAdd.setOnClickListener {
            nomProd.add(textProd.text.toString())
            arrayAdapter.notifyDataSetChanged()
        }

        listProd.setOnItemClickListener { parent, view, position, id ->
            val element = arrayAdapter.getItem(position)
            nomProd.remove(element)
            arrayAdapter.notifyDataSetChanged()
        }

    }

}