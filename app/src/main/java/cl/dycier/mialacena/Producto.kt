package cl.dycier.mialacena
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "productos")
data class Producto(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val nombre: String,
    var cantidad: Int,
    var precio: Int,
    val categoria: String
)
