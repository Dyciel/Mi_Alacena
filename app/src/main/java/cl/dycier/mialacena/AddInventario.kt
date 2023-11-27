package cl.dycier.mialacena

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextMenu
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.Spinner
import androidx.appcompat.app.AlertDialog

class AddInventario : AppCompatActivity(), ActualizableInventario, ActualizablePrecio {

    private val listaProductos: MutableList<Producto> = mutableListOf()
    private lateinit var adapter: ProductoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_inventario)
        setSupportActionBar(findViewById(R.id.toobar))

        val listProd = findViewById<ListView>(R.id.listaObjetos)

        adapter = ProductoAdapter(this, listaProductos)
        listProd.adapter = adapter

        val btnAdd = findViewById<Button>(R.id.addButton)
        registerForContextMenu(listProd)

        btnAdd.setOnClickListener {
            mostrarFormularioAgregarProducto()
        }
        listProd.setOnItemClickListener { parent, view, position, id ->
            val productoSeleccionado = listaProductos[position]

            val builder = AlertDialog.Builder(this)
            val inflater = layoutInflater
            val dialogView = inflater.inflate(R.layout.editar_producto, null)

            val editTextCantidad = dialogView.findViewById<EditText>(R.id.editTextCantidad)
            val editTextPrecio = dialogView.findViewById<EditText>(R.id.editTextPrecio)

            editTextCantidad.setText(productoSeleccionado.cantidad.toString())
            editTextPrecio.setText(productoSeleccionado.precio.toString())

            builder.setView(dialogView)
                .setTitle("Editar Producto")
                .setPositiveButton("Guardar") { dialog, which ->
                    val nuevaCantidad = editTextCantidad.text.toString().toIntOrNull() ?: 0
                    val nuevoPrecio = editTextPrecio.text.toString().toIntOrNull() ?: 0

                    actualizarInventario(productoSeleccionado.nombre, nuevaCantidad)
                    actualizarPrecio(productoSeleccionado.nombre, nuevoPrecio)
                }
                .setNegativeButton("Cancelar") { dialog, which -> dialog.cancel() }

            val dialog = builder.create()
            dialog.show()
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
            R.id.AbAcercade -> {
                val intent = Intent(this@AddInventario, Info::class.java)
                startActivity(intent)
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
            val productosPorNombreYCateg = listaProductos.filter {
                it.nombre.equals(nombre, ignoreCase = true) && it.categoria.equals(categoria, ignoreCase = true)
            }
            resultadosBusqueda.addAll(productosPorNombreYCateg)
        } else if (nombre.isNotEmpty()) {
            val productosPorNombre = listaProductos.filter {
                it.nombre.equals(nombre, ignoreCase = true)
            }
            resultadosBusqueda.addAll(productosPorNombre)
        } else if (categoria.isNotEmpty()) {
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
    }

    override fun actualizarPrecio(nombreProducto: String, nuevoPrecio: Int) {
        val producto = listaProductos.find { it.nombre == nombreProducto }
        producto?.precio = nuevoPrecio
        adapter.notifyDataSetChanged()
    }

    private fun imprimirListaProductos() {
        println("Lista de productos:")
        listaProductos.forEach { println(it) }
    }
    override fun onCreateContextMenu(menu: ContextMenu?, v: View?, menuInfo: ContextMenu.ContextMenuInfo?) {
        super.onCreateContextMenu(menu, v, menuInfo)
        menuInflater.inflate(R.menu.context_menu, menu)
    }
    override fun onContextItemSelected(item: MenuItem): Boolean {
        val info = item.menuInfo as AdapterView.AdapterContextMenuInfo
        val position = info.position

        return when (item.itemId) {
            R.id.editar_producto -> {
                mostrarFormularioEditarProducto(position)
                true
            }
            R.id.eliminar_producto -> {
                eliminarProducto(position)
                true
            }
            else -> super.onContextItemSelected(item)
        }
    }
    private fun mostrarFormularioEditarProducto(position: Int) {
        val productoSeleccionado = listaProductos[position]

        val builder = AlertDialog.Builder(this)
        val inflater = layoutInflater
        val dialogView = inflater.inflate(R.layout.editar_producto, null)

        val editTextCantidad = dialogView.findViewById<EditText>(R.id.editTextCantidad)
        val editTextPrecio = dialogView.findViewById<EditText>(R.id.editTextPrecio)

        editTextCantidad.setText(productoSeleccionado.cantidad.toString())
        editTextPrecio.setText(productoSeleccionado.precio.toString())

        builder.setView(dialogView)
            .setTitle("Editar Producto")
            .setPositiveButton("Guardar") { dialog, which ->
                val nuevaCantidad = editTextCantidad.text.toString().toIntOrNull() ?: 0
                val nuevoPrecio = editTextPrecio.text.toString().toIntOrNull() ?: 0

                actualizarInventario(productoSeleccionado.nombre, nuevaCantidad)
                actualizarPrecio(productoSeleccionado.nombre, nuevoPrecio)
            }
            .setNegativeButton("Cancelar") { dialog, which -> dialog.cancel() }

        val dialog = builder.create()
        dialog.show()
    }

    private fun eliminarProducto(position: Int) {
        listaProductos.removeAt(position)
        adapter.notifyDataSetChanged()
    }
}