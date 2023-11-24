package cl.dycier.mialacena

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

class ProductoAdapter(context: Context, private var listaProductos: MutableList<Producto>) :
    ArrayAdapter<Producto>(context, 0, listaProductos) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var itemView = convertView
        if (itemView == null) {
            itemView = LayoutInflater.from(context).inflate(android.R.layout.simple_list_item_1, parent, false)
        }

        val currentItem = listaProductos[position]
        val textView = itemView?.findViewById<TextView>(android.R.id.text1)
        textView?.text = "${currentItem.nombre} - ${currentItem.cantidad} - ${currentItem.precio} - ${currentItem.categoria}"

        return itemView!!
    }
    fun actualizarLista(nuevaLista: MutableList<Producto>) {
        listaProductos.clear()
        listaProductos.addAll(nuevaLista)
        notifyDataSetChanged()
    }

}