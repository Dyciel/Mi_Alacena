package cl.dycier.mialacena

interface ActualizableInventario {
    fun actualizarInventario(nombreProducto: String, nuevaCantidad: Int)
}

interface ActualizablePrecio {
    fun actualizarPrecio(nombreProducto: String, nuevoPrecio: Int)
}