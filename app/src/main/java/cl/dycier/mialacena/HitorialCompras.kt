package cl.dycier.mialacena

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import androidx.appcompat.app.AlertDialog

data class Date(val day:String, val number:Int, val month:String, val year:Int)
class HitorialCompras : AppCompatActivity() {
    lateinit var buttonBack: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hitorial_compras)

        val arrayAdapter: ArrayAdapter<*>
        val listFecha = findViewById<ListView>(R.id.listDate)

        val Day1 = Date("Lun", 10, "Jul", 2023)
        val Day2 = Date("Jue", 3, "Ago", 2023)
        val Day3 = Date("Mar", 22, "Ago", 2023)
        val Day4 = Date("Vie", 15, "Sept", 2023)
        val Day5 = Date("Sáb", 7, "Oct", 2023)

        val listDate = mutableListOf(Day1, Day2, Day3, Day4, Day5)

        val item1 = Producto("Jugos", 3,4790)
        val item2 = Producto("Mayonesa", 1,1790)
        val item3 = Producto("Salsa de Tomates", 6,1490)

        val item4 = Producto("Galletas", 4,2390)
        val item5 = Producto("Cereales", 1,3490)
        val item6 = Producto("Manjar", 1,1290)

        val listItem = mutableListOf(item1, item2, item3, item4, item5, item6)

        val listString = mutableListOf<String>()

        for (dia in listDate){
            listString.add("${dia.day} ${dia.number} / ${dia.month} / ${dia.year}")
        }
        arrayAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, listString)

        listFecha.adapter = arrayAdapter

        listFecha.setOnItemClickListener { adapterView, view, position, l ->

            val objeto = listItem[position]
            AlertDialog.Builder(this).apply{
                setTitle("Artículos Comprados: ")
                setMessage("${objeto.nombreItem} \nCant: ${objeto.cantItem}\n Precio: ${objeto.precioItem}")
                setPositiveButton("Atrás", null)

            }.show()
        }


        buttonBack=findViewById(R.id.backButton6)

        buttonBack.setOnClickListener{
            val intent = Intent(this@HitorialCompras, ListaCompras::class.java)
            startActivity(intent)
            finish()
        }


    }
}