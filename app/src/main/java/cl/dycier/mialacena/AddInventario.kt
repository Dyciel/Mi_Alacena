package cl.dycier.mialacena

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.Spinner
import androidx.appcompat.app.AlertDialog

class AddInventario : AppCompatActivity(), ActualizableInventario, ActualizablePrecio {

    private val listaProductos: MutableList<Producto> = mutableListOf()
    private lateinit var adapter: ProductoAdapter // Agregar esta variable para el adaptador

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_inventario)
        setSupportActionBar(findViewById(R.id.toobar))

        val listProd = findViewById<ListView>(R.id.listaObjetos)


        // Crear el adaptador con la lista de productos
        adapter = ProductoAdapter(this, listaProductos)
        listProd.adapter = adapter

        // Agregar evento al botón para agregar un producto a la lista
        val btnAdd = findViewById<Button>(R.id.addButton)

        btnAdd.setOnClickListener {
            mostrarFormularioAgregarProducto()
        }
    }

    private fun agregarProducto(nombre: String, cantidad: Int, precio: Int, categoria: String) {
        val producto = Producto(
            nombre = nombre,
            cantidad = cantidad,
            precio = precio,
            categoria = categoria
        )
        listaProductos.add(producto)
        imprimirListaProductos()
    }
    private fun mostrarFormularioAgregarProducto() {
        val builder = AlertDialog.Builder(this)
        val inflater = layoutInflater
        val dialogView = inflater.inflate(R.layout.datosadd, null)

        val editTextNombre = dialogView.findViewById<EditText>(R.id.editTextNombre)
        val editTextCantidad = dialogView.findViewById<EditText>(R.id.editTextCantidad)
        val editTextPrecio = dialogView.findViewById<EditText>(R.id.editTextPrecio)
        val spinnerCategoria = dialogView.findViewById<Spinner>(R.id.spinnerCategoria)

        val categoriasAdapter = ArrayAdapter.createFromResource(
            this,
            R.array.categorias,
            android.R.layout.simple_spinner_item
        )
        categoriasAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerCategoria.adapter = categoriasAdapter

        builder.setView(dialogView)
            .setTitle("Agregar Producto")
            .setPositiveButton("Agregar") { dialog, which ->
                val nombre = editTextNombre.text.toString()
                val cantidad = editTextCantidad.text.toString().toIntOrNull() ?: 0
                val precio = editTextPrecio.text.toString().toIntOrNull() ?: 0
                val categoria = spinnerCategoria.selectedItem.toString()

                agregarProducto(nombre, cantidad, precio, categoria)
                adapter.notifyDataSetChanged()
            }
            .setNegativeButton("Cancelar") { dialog, which -> dialog.cancel() }

        val dialog = builder.create()
        dialog.show()
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.Busq -> {
                mostrarFormularioBusqueda()
                return true
            }

            R.id.Orden -> {
                mostrarFormularioOrden()
                return true
            }

            R.id.PrefConf -> {
                val intent = Intent(this@AddInventario, Settings::class.java)
                startActivity(intent)
                return true
            }

            R.id.AbAcercade -> {
                return true
            }

            R.id.OrdenarPorNombre -> {
                ordenarPorNombre()
                return true
            }

            R.id.OrdenarPorCantidad -> {
                ordenarPorCantidad()
                return true
            }

            R.id.OrdenarPorCategoria -> {
                ordenarPorCategoria()
                return true
            }

            else -> return super.onOptionsItemSelected(item)
        }

    }
    private fun mostrarFormularioBusqueda() {
        val builder = AlertDialog.Builder(this)
        val inflater = layoutInflater
        val dialogView = inflater.inflate(R.layout.busqueda, null)

        val editTextNombre = dialogView.findViewById<EditText>(R.id.editTextNombre)
        val spinnerCategoria = dialogView.findViewById<Spinner>(R.id.spinnerCategoria)

        val categoriasAdapter = ArrayAdapter.createFromResource(
            this,
            R.array.categorias,
            android.R.layout.simple_spinner_item
        )
        categoriasAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerCategoria.adapter = categoriasAdapter

        builder.setView(dialogView)
            .setTitle("Buscar Producto")
            .setPositiveButton("Buscar") { dialog, which ->
                val nombre = editTextNombre.text.toString()
                val categoria = spinnerCategoria.selectedItem.toString()

                realizarBusqueda(nombre, categoria)
            }
            .setNegativeButton("Cancelar") { dialog, which -> dialog.cancel() }

        val dialog = builder.create()
        dialog.show()
    }
    private fun realizarBusqueda(nombre: String, categoria: String) {
        val resultadosBusqueda = mutableListOf<Producto>()

        if (nombre.isNotEmpty() && categoria.isNotEmpty()) {
            // Buscar por nombre y categoría
            val productosPorNombreYCateg = listaProductos.filter {
                it.nombre.equals(nombre, ignoreCase = true) && it.categoria.equals(categoria, ignoreCase = true)
            }
            resultadosBusqueda.addAll(productosPorNombreYCateg)
        } else if (nombre.isNotEmpty()) {
            // Buscar solo por nombre
            val productosPorNombre = listaProductos.filter {
                it.nombre.equals(nombre, ignoreCase = true)
            }
            resultadosBusqueda.addAll(productosPorNombre)
        } else if (categoria.isNotEmpty()) {
            // Buscar solo por categoría
            val productosPorCategoria = listaProductos.filter {
                it.categoria.equals(categoria, ignoreCase = true)
            }
            resultadosBusqueda.addAll(productosPorCategoria)
        }

        adapter.actualizarLista(resultadosBusqueda)
        imprimirListaProductos()
    }
    private fun mostrarFormularioOrden() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Ordenar Por")

        val options = arrayOf("Nombre", "Cantidad", "Categoría")

        builder.setItems(options) { dialog, which ->
            when (which) {
                0 -> ordenarPorNombre()
                1 -> ordenarPorCantidad()
                2 -> ordenarPorCategoria()
            }
        }

        builder.setNegativeButton("Cancelar") { dialog, _ ->
            dialog.dismiss()
        }

        val dialog = builder.create()
        dialog.show()
    }

    private fun ordenarPorNombre() {
        listaProductos.sortBy { it.nombre }
        adapter.notifyDataSetChanged()
    }

    private fun ordenarPorCantidad() {
        listaProductos.sortBy { it.cantidad }
        adapter.notifyDataSetChanged()
    }

    private fun ordenarPorCategoria() {
        listaProductos.sortBy { it.categoria }
        adapter.notifyDataSetChanged()
    }

    override fun actualizarInventario(nombreProducto: String, nuevaCantidad: Int) {
        val producto = listaProductos.find { it.nombre == nombreProducto }
        producto?.cantidad = nuevaCantidad
        adapter.notifyDataSetChanged()
        imprimirListaProductos()
    }

    override fun actualizarPrecio(nombreProducto: String, nuevoPrecio: Int) {
        val producto = listaProductos.find { it.nombre == nombreProducto }
        producto?.precio = nuevoPrecio
        adapter.notifyDataSetChanged()
        imprimirListaProductos()
    }

    private fun imprimirListaProductos() {
        println("Lista de productos:")
        listaProductos.forEach { println(it) }
    }
}