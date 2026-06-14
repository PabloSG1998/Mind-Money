package com.mindmoney.ui.finanzas
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mindmoney.R
import android.app.AlertDialog
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import android.widget.TextView
import com.mindmoney.Transaccion

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [FinanzasFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class FinanzasFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private val listaTransacciones = mutableListOf<Transaccion>()
    private lateinit var titulosTransacciones: List<TextView>
    private lateinit var fechasTransacciones: List<TextView>
    private lateinit var montosTransacciones: List<TextView>
    private lateinit var iconosTransacciones: List<TextView>
    private var totalIngresos = 0.0
    private var totalGastos = 0.0
    private var emojiSeleccionado = ""
    //Guardar info
    private val PREFS = "MindMoneyPrefs"
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val vista = inflater.inflate(
            R.layout.fragment_finanzas,
            container,
            false
        )
        val textSaldo =
            vista.findViewById<TextView>(R.id.textSaldoFinanzas)
        val textIngresos =
            vista.findViewById<TextView>(R.id.textIngresos)
        val textGastos =
            vista.findViewById<TextView>(R.id.textGastos)
        val tituloTransaccion1 =
            vista.findViewById<TextView>(R.id.tituloTransaccion1)
        val fechaTransaccion1 =
            vista.findViewById<TextView>(R.id.fechaTransaccion1)
        val montoTransaccion1 =
            vista.findViewById<TextView>(R.id.montoTransaccion1)
        val iconoTransaccion1 =
            vista.findViewById<TextView>(R.id.iconoTransaccion1)

        titulosTransacciones = listOf(
            vista.findViewById(R.id.tituloTransaccion1),
            vista.findViewById(R.id.tituloTransaccion2),
            vista.findViewById(R.id.tituloTransaccion3),
            vista.findViewById(R.id.tituloTransaccion4),
            vista.findViewById(R.id.tituloTransaccion5),
            vista.findViewById(R.id.tituloTransaccion6),
            vista.findViewById(R.id.tituloTransaccion7),
            vista.findViewById(R.id.tituloTransaccion8),
            vista.findViewById(R.id.tituloTransaccion9),
            vista.findViewById(R.id.tituloTransaccion10)
        )
        fechasTransacciones = listOf(
            vista.findViewById(R.id.fechaTransaccion1),
            vista.findViewById(R.id.fechaTransaccion2),
            vista.findViewById(R.id.fechaTransaccion3),
            vista.findViewById(R.id.fechaTransaccion4),
            vista.findViewById(R.id.fechaTransaccion5),
            vista.findViewById(R.id.fechaTransaccion6),
            vista.findViewById(R.id.fechaTransaccion7),
            vista.findViewById(R.id.fechaTransaccion8),
            vista.findViewById(R.id.fechaTransaccion9),
            vista.findViewById(R.id.fechaTransaccion10)
        )
        montosTransacciones = listOf(
            vista.findViewById(R.id.montoTransaccion1),
            vista.findViewById(R.id.montoTransaccion2),
            vista.findViewById(R.id.montoTransaccion3),
            vista.findViewById(R.id.montoTransaccion4),
            vista.findViewById(R.id.montoTransaccion5),
            vista.findViewById(R.id.montoTransaccion6),
            vista.findViewById(R.id.montoTransaccion7),
            vista.findViewById(R.id.montoTransaccion8),
            vista.findViewById(R.id.montoTransaccion9),
            vista.findViewById(R.id.montoTransaccion10)
        )
        iconosTransacciones = listOf(
            vista.findViewById(R.id.iconoTransaccion1),
            vista.findViewById(R.id.iconoTransaccion2),
            vista.findViewById(R.id.iconoTransaccion3),
            vista.findViewById(R.id.iconoTransaccion4),
            vista.findViewById(R.id.iconoTransaccion5),
            vista.findViewById(R.id.iconoTransaccion6),
            vista.findViewById(R.id.iconoTransaccion7),
            vista.findViewById(R.id.iconoTransaccion8),
            vista.findViewById(R.id.iconoTransaccion9),
            vista.findViewById(R.id.iconoTransaccion10)
        )
        //Cargar datos, historial
        cargarDatos()
        textIngresos.text = "+$${totalIngresos}"
        textGastos.text = "-$${totalGastos}"
        textSaldo.text = "$${totalIngresos - totalGastos}"
        actualizarHistorial()

        val cardIngresos = vista.findViewById<androidx.cardview.widget.CardView>(R.id.cardIngresos)
        val cardGastos = vista.findViewById<androidx.cardview.widget.CardView>(R.id.cardGastos)
        val buttonReiniciar = vista.findViewById<Button>(R.id.buttonEditar)

        //Ingresos
        cardIngresos.setOnClickListener {
            val dialogView = LayoutInflater.from(requireContext())
                .inflate(R.layout.dialog_ingreso, null)
            val dialog = AlertDialog.Builder(requireContext())
                .setView(dialogView)
                .create()
            dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
            val editTitulo =
                dialogView.findViewById<EditText>(R.id.editTitulo)
            val editCantidad =
                dialogView.findViewById<EditText>(R.id.editCantidad)
            val iconCafe =
                dialogView.findViewById<Button>(R.id.iconCafe)
            val iconTransporte =
                dialogView.findViewById<Button>(R.id.iconTransporte)
            val iconHogar =
                dialogView.findViewById<Button>(R.id.iconHogar)
            val iconInversion =
                dialogView.findViewById<Button>(R.id.iconInversion)
            val iconServicios =
                dialogView.findViewById<Button>(R.id.iconServicios)
            val iconOtros =
                dialogView.findViewById<Button>(R.id.iconOtros)
            emojiSeleccionado = ""
            val listaBotones = listOf(
                iconCafe,
                iconTransporte,
                iconHogar,
                iconInversion,
                iconServicios,
                iconOtros
            )
            for (boton in listaBotones) {
                boton.setOnClickListener {
                    for (b in listaBotones) {
                        b.alpha = 1.0f
                    }
                    boton.alpha = 0.5f
                    emojiSeleccionado = boton.text.toString()
                }
            }
            val botonGuardar =
                dialogView.findViewById<Button>(R.id.buttonGuardarIngreso)
            val botonCancelar =
                dialogView.findViewById<Button>(R.id.buttonCancelarIngreso)

            botonCancelar.setOnClickListener {
                dialog.dismiss()
            }

            botonGuardar.setOnClickListener {

                val titulo = editTitulo.text.toString().trim()
                val cantidadTexto = editCantidad.text.toString().trim()

                if (titulo.isEmpty() || cantidadTexto.isEmpty()) {
                    Toast.makeText(
                        requireContext(),
                        "Completa todos los campos",
                        Toast.LENGTH_SHORT
                    ).show()
                    return@setOnClickListener
                }

                val cantidad = cantidadTexto.toDouble()
                val nuevaTransaccion = Transaccion(
                    titulo = titulo,
                    cantidad = cantidad,
                    categoria = emojiSeleccionado,
                    esIngreso = true
                )

                listaTransacciones.add(0, nuevaTransaccion)
                //eliminar transacción 11
                if (listaTransacciones.size > 10) {
                    listaTransacciones.removeAt(10)
                }

                totalIngresos += cantidad
                actualizarHistorial()
                guardarDatos()

                textIngresos.text = "+$${totalIngresos}"

                val saldoActual = totalIngresos - totalGastos

                textSaldo.text = "$$saldoActual"
                tituloTransaccion1.text = titulo
                fechaTransaccion1.text = "Hoy"
                montoTransaccion1.text = "+$${cantidad}"
                montoTransaccion1.setTextColor(
                    android.graphics.Color.parseColor("#00C84F")
                )

                iconoTransaccion1.text = emojiSeleccionado
                Toast.makeText(
                    requireContext(),
                    "Ingreso guardado",
                    Toast.LENGTH_SHORT
                ).show()
                dialog.dismiss()
            }
            dialog.show()
        }

        //Gastos
        cardGastos.setOnClickListener {

            val dialogView = LayoutInflater.from(requireContext())
                .inflate(R.layout.dialog_gasto, null)
            //Emojis
            val iconCafe =
                dialogView.findViewById<Button>(R.id.iconCafe)
            val iconTransporte =
                dialogView.findViewById<Button>(R.id.iconTransporte)
            val iconHogar =
                dialogView.findViewById<Button>(R.id.iconHogar)
            val iconInversion =
                dialogView.findViewById<Button>(R.id.iconInversion)
            val iconServicios =
                dialogView.findViewById<Button>(R.id.iconServicios)
            val iconOtros =
                dialogView.findViewById<Button>(R.id.iconOtros)
            emojiSeleccionado = ""
            val listaBotones = listOf(
                iconCafe,
                iconTransporte,
                iconHogar,
                iconInversion,
                iconServicios,
                iconOtros
            )
            for (boton in listaBotones) {
                boton.setOnClickListener {
                    for (b in listaBotones) {
                        b.alpha = 1.0f
                    }
                    boton.alpha = 0.5f
                    emojiSeleccionado =
                        boton.text.toString()
                }
            }

            val dialog = AlertDialog.Builder(requireContext())
                .setView(dialogView)
                .create()
            dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
            dialog.show()
            val botonCancelar =
                dialogView.findViewById<Button>(R.id.buttonCancelarGasto)
            val botonGuardar =
                dialogView.findViewById<Button>(R.id.buttonGuardarGasto)


            botonCancelar.setOnClickListener {
                dialog.dismiss()
            }
            botonGuardar.setOnClickListener {
                val titulo =
                    dialogView.findViewById<android.widget.EditText>(R.id.editTitulo)
                        .text.toString()
                val cantidadTexto =
                    dialogView.findViewById<android.widget.EditText>(R.id.editCantidad)
                        .text.toString()

                if (titulo.isBlank() || cantidadTexto.isBlank()) {
                    android.widget.Toast.makeText(
                        requireContext(),
                        "Completa todos los campos",
                        android.widget.Toast.LENGTH_SHORT
                    ).show()
                    return@setOnClickListener
                }

                val cantidad = cantidadTexto.toDouble()
                val nuevaTransaccion = Transaccion(
                    titulo = titulo,
                    cantidad = cantidad,
                    categoria = emojiSeleccionado,
                    esIngreso = false
                )

                listaTransacciones.add(0, nuevaTransaccion)
                //Borrar transacción 11
                if (listaTransacciones.size > 10) {
                    listaTransacciones.removeAt(10)
                }

                totalGastos += cantidad
                actualizarHistorial()
                guardarDatos()

                textGastos.text = "-$${totalGastos}"

                val saldoActual = totalIngresos - totalGastos

                textSaldo.text = "$$saldoActual"
                tituloTransaccion1.text = titulo
                fechaTransaccion1.text = "Hoy"
                montoTransaccion1.text = "-$${cantidad}"
                montoTransaccion1.setTextColor(
                    android.graphics.Color.parseColor("#D86367")
                )

                iconoTransaccion1.text = emojiSeleccionado
                android.widget.Toast.makeText(
                    requireContext(),
                    "Gasto guardado",
                    android.widget.Toast.LENGTH_SHORT
                ).show()
                dialog.dismiss()
            }
        }

        buttonReiniciar.setOnClickListener {
            val dialogView = LayoutInflater.from(requireContext())
                .inflate(R.layout.dialog_reiniciar, null)

            val dialog = AlertDialog.Builder(requireContext())
                .setView(dialogView)
                .create()
            dialog.window?.setBackgroundDrawableResource(
                android.R.color.transparent
            )
            dialog.show()
            val buttonCancelar =
                dialogView.findViewById<Button>(
                    R.id.buttonCancelarReinicio
                )
            val buttonBorrar =
                dialogView.findViewById<Button>(
                    R.id.buttonBorrarDatos
                )
            buttonCancelar.setOnClickListener {
                dialog.dismiss()
            }
            //Reiniciar
            buttonBorrar.setOnClickListener {
                listaTransacciones.clear()
                totalIngresos = 0.0
                totalGastos = 0.0

                textIngresos.text = "$0.0"
                textGastos.text = "$0.0"
                textSaldo.text = "$0.0"
                actualizarHistorial()
                guardarDatos()

                Toast.makeText(
                    requireContext(),
                    "Datos eliminados",
                    Toast.LENGTH_SHORT
                ).show()
                dialog.dismiss()
            }
        }

        return vista
    }

    private fun actualizarHistorial() {
        for (i in 0 until 10) {
            if (i < listaTransacciones.size) {
                val transaccion = listaTransacciones[i]
                titulosTransacciones[i].text =
                    transaccion.titulo
                fechasTransacciones[i].text =
                    "Hoy"
                if (transaccion.esIngreso) {
                    montosTransacciones[i].text =
                        "+$${transaccion.cantidad}"
                    montosTransacciones[i].setTextColor(
                        android.graphics.Color.parseColor("#00C84F")
                    )
                } else {
                    montosTransacciones[i].text =
                        "-$${transaccion.cantidad}"

                    montosTransacciones[i].setTextColor(
                        android.graphics.Color.parseColor("#D86367")
                    )
                }
                iconosTransacciones[i].text =
                    transaccion.categoria
            } else {
                titulosTransacciones[i].text =
                    "Sin movimientos"
                fechasTransacciones[i].text =
                    "--/--/----"
                montosTransacciones[i].text =
                    "$0"
                montosTransacciones[i].setTextColor(
                    android.graphics.Color.WHITE
                )
                iconosTransacciones[i].text = ""
            }
        }
    }
    //Guardar historial
    private fun guardarDatos() {
        val prefs = requireContext()
            .getSharedPreferences(PREFS, android.content.Context.MODE_PRIVATE)
        val editor = prefs.edit()

        editor.putFloat(
            "totalIngresos",
            totalIngresos.toFloat()
        )
        editor.putFloat(
            "totalGastos",
            totalGastos.toFloat()
        )

        val historialTexto =
            listaTransacciones.joinToString("|||") {

                "${it.titulo}###${it.cantidad}###${it.categoria}###${it.esIngreso}"
            }

        editor.putString(
            "historial",
            historialTexto
        )
        editor.apply()
    }

    //Cargar historial
    private fun cargarDatos() {

        val prefs = requireContext()
            .getSharedPreferences(PREFS, android.content.Context.MODE_PRIVATE)

        totalIngresos =
            prefs.getFloat(
                "totalIngresos",
                0f
            ).toDouble()
        totalGastos =
            prefs.getFloat(
                "totalGastos",
                0f
            ).toDouble()

        listaTransacciones.clear()

        val historialTexto =
            prefs.getString(
                "historial",
                ""
            ) ?: ""
        if (historialTexto.isNotEmpty()) {
            val registros =
                historialTexto.split("|||")
            for (registro in registros) {
                val partes =
                    registro.split("###")
                if (partes.size == 4) {
                    listaTransacciones.add(
                        Transaccion(
                            titulo = partes[0],
                            cantidad = partes[1].toDouble(),
                            categoria = partes[2],
                            esIngreso = partes[3].toBoolean()
                        )
                    )
                }
            }
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment FinanzasFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            FinanzasFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}