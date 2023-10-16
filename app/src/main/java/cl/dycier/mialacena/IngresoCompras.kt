package cl.dycier.mialacena

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import androidx.appcompat.app.AlertDialog

class IngresoCompras : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ingreso_compras)

        val listP = findViewById<ListView>(R.id.listIngShop)
        val btnAdd = findViewById<Button>(R.id.buttonAgregar)
        val arrayAdapter: ArrayAdapter<*>
        val nomProd2 = findViewById<EditText>(R.id.nameProducto2)
        val cantProd = findViewById<EditText>(R.id.cantProducto2)
        val preProd = findViewById<EditText>(R.id.prizaProducto)

        val item1 = Producto("Leche", 3,2790)
        val item2 = Producto("Mantequilla", 1,1270)
        val item3 = Producto("Yogurt", 6,1890)

        val lista = mutableListOf(item1, item2, item3)


        val listString = mutableListOf<String>()

        for (objeto in lista){
            listString.add("${objeto.nombreItem}")
        }

        arrayAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, listString)

        listP.adapter = arrayAdapter

        btnAdd.setOnClickListener {
            listString.add(nomProd2.text.toString())
            arrayAdapter.notifyDataSetChanged()

            nomProd2.setText("")
            cantProd.setText("")
            preProd.setText("")
        }

        listP.setOnItemClickListener { adapterView, view, posicion, l ->

            val objeto = lista[posicion]

            AlertDialog.Builder(this).apply{
                setTitle("${objeto.nombreItem}")
                setMessage("Cantidad: ${objeto.cantItem} \nPrecio: ${objeto.precioItem}")
                setPositiveButton("Atrás", null)

            }.show()
        }
    }
    //listString.add(nomProd2.text.toString())
    //arrayAdapter.notifyDataSetChanged()

}

