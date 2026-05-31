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
    private var totalIngresos = 0.0
    private var totalGastos = 0.0
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
        val cardIngresos = vista.findViewById<androidx.cardview.widget.CardView>(R.id.cardIngresos)
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
                    categoria = "📈",
                    esIngreso = true
                )

                listaTransacciones.add(0, nuevaTransaccion)
                totalIngresos += cantidad
                textIngresos.text = "+$${totalIngresos}"

                val saldoActual = totalIngresos - totalGastos

                textSaldo.text = "$$saldoActual"
                tituloTransaccion1.text = titulo
                fechaTransaccion1.text = "Hoy"
                montoTransaccion1.text = "+$${cantidad}"
                montoTransaccion1.setTextColor(
                    android.graphics.Color.parseColor("#00C84F")
                )

                iconoTransaccion1.text = "📈"
                Toast.makeText(
                    requireContext(),
                    "Ingreso guardado",
                    Toast.LENGTH_SHORT
                ).show()
                dialog.dismiss()
            }
            dialog.show()
        }
        return vista
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