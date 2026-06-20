package com.mindmoney.ui.inicio

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mindmoney.R
import android.widget.TextView
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [InicioFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class InicioFragment : Fragment() {
    // TODO: Rename and change types of parameters
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
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val view = inflater.inflate(
            R.layout.fragment_inicio,
            container,
            false
        )
        val textSaldo =
            view.findViewById<TextView>(R.id.textSaldo)
        val textMensajeAdmin =
            view.findViewById<TextView>(R.id.textMensajeAdmin)
        val textFechaActualizacion =
            view.findViewById<TextView>(R.id.textFechaActualizacion)
        val textTareaInicio1 =
            view.findViewById<TextView>(R.id.textTareaInicio1)
        val textTareaInicio2 =
            view.findViewById<TextView>(R.id.textTareaInicio2)
        val textTareaInicio3 =
            view.findViewById<TextView>(R.id.textTareaInicio3)
        val textPendientes =
            view.findViewById<TextView>(R.id.textPendientes)
        val sharedPreferences =
            requireContext().getSharedPreferences(
                "MindMoneyPrefs",
                android.content.Context.MODE_PRIVATE
            )
        val ingresos =
            sharedPreferences.getFloat(
                "totalIngresos",
                0f
            ).toDouble()
        val gastos =
            sharedPreferences.getFloat(
                "totalGastos",
                0f
            ).toDouble()
        val saldo = ingresos - gastos
        textSaldo.text = "$$saldo"
        val mensajeAdmin =
            sharedPreferences.getString(
                "mensajeAdmin",
                "Recuerda registrar tus gastos diarios para mantener un mejor control financiero."
            )
        val fechaAdmin =
            sharedPreferences.getString(
                "fechaMensajeAdmin",
                "--/--/--"
            )
        textMensajeAdmin.text =
            mensajeAdmin
        textFechaActualizacion.text =
            "Actualizado: $fechaAdmin"
        val textoTareas =
            sharedPreferences.getString(
                "tareas",
                ""
            ) ?: ""

        if (textoTareas.isNotEmpty()) {
            val registros =
                textoTareas.split("|||")
            val tareas =
                registros.mapNotNull {

                    val partes =
                        it.split("###")

                    if (partes.size == 2)
                        partes[0]
                    else
                        null
                }
            textTareaInicio1.text =
                tareas.getOrNull(0)
                    ?: "Sin tareas"
            textTareaInicio2.text =
                tareas.getOrNull(1)
                    ?: "Sin tareas"
            textTareaInicio3.text =
                tareas.getOrNull(2)
                    ?: "Sin tareas"
            textPendientes.text =
                "${tareas.size} tareas pendientes"
        } else {
            textTareaInicio1.text =
                "Sin tareas"
            textTareaInicio2.text =
                "Sin tareas"
            textTareaInicio3.text =
                "Sin tareas"
            textPendientes.text =
                "0 tareas pendientes"
        }
        val botonLogin =
            view.findViewById<Button>(R.id.login)
        botonLogin.setOnClickListener {
            mostrarLoginAdmin()
        }
        return view
    }

    private fun mostrarLoginAdmin() {

        val layout =
            android.widget.LinearLayout(requireContext())
        layout.orientation =
            android.widget.LinearLayout.VERTICAL
        layout.setPadding(50, 40, 50, 20)
        val usuario =
            EditText(requireContext())
        usuario.hint = "Usuario"
        val password =
            EditText(requireContext())
        password.hint = "Contraseña"
        val ayuda =
            TextView(requireContext())
        ayuda.text =
            "User: Pablo | Pass: 123"
        ayuda.rotation = 180f
        ayuda.alpha = 0.15f
        layout.addView(usuario)
        layout.addView(password)
        layout.addView(ayuda)

        AlertDialog.Builder(requireContext())
            .setTitle("Iniciar sesión")
            .setView(layout)
            .setPositiveButton("Ingresar") { _, _ ->
                if (
                    usuario.text.toString() == "Pablo"
                    &&
                    password.text.toString() == "123"
                ) {
                    abrirPanelAdmin()
                } else {
                    android.widget.Toast.makeText(
                        requireContext(),
                        "Usuario no existe",
                        android.widget.Toast.LENGTH_SHORT
                    ).show()
                }
            }
            .setNegativeButton(
                "Cancelar",
                null
            )

            .show()
    }

    private fun abrirPanelAdmin() {

        val editorMensaje =
            EditText(requireContext())
        editorMensaje.hint =
            "Escribe el mensaje del administrador"
        editorMensaje.minLines = 5
        val prefs =
            requireContext().getSharedPreferences(
                "MindMoneyPrefs",
                android.content.Context.MODE_PRIVATE
            )
        editorMensaje.setText(
            prefs.getString(
                "mensajeAdmin",
                ""
            )
        )

        AlertDialog.Builder(requireContext())
            .setTitle("Mensaje del Administrador")
            .setView(editorMensaje)
            .setPositiveButton("Publicar") { _, _ ->
                val mensaje =
                    editorMensaje.text.toString()
                val fechaActual =
                    java.text.SimpleDateFormat(
                        "dd/MM/yy",
                        java.util.Locale.getDefault()
                    ).format(java.util.Date())

                prefs.edit()
                    .putString(
                        "mensajeAdmin",
                        mensaje
                    )
                    .putString(
                        "fechaMensajeAdmin",
                        fechaActual
                    )
                    .apply()
                android.widget.Toast.makeText(
                    requireContext(),
                    "Mensaje publicado",
                    android.widget.Toast.LENGTH_SHORT
                ).show()
            }
            .setNegativeButton(
                "Cancelar",
                null
            )
            .show()
    }
    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment InicioFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            InicioFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}