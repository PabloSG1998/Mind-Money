package com.mindmoney.ui.mensajes

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mindmoney.R
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [MensajesFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MensajesFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var textMensajeAdminMensajes: TextView
    private lateinit var textFechaActualizacion: TextView

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
            R.layout.fragment_mensajes,
            container,
            false
        )
        val botonLogin =
            view.findViewById<Button>(R.id.login)

        textMensajeAdminMensajes =
            view.findViewById(R.id.textMensajeAdminMensajes)
        textFechaActualizacion =
            view.findViewById(R.id.textFechaActualizacion)

        botonLogin.setOnClickListener {
            mostrarLoginAdmin()
        }
        cargarMensajeAdmin()
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
                guardarMensajeAdmin(mensaje)
            }
            .setNegativeButton(
                "Cancelar",
                null
            )
            .show()
    }

    private fun guardarMensajeAdmin(
        mensaje: String
    ) {
        val prefs =
            requireContext().getSharedPreferences(
                "MindMoneyPrefs",
                android.content.Context.MODE_PRIVATE
            )
        val formatoFecha =
            java.text.SimpleDateFormat(
                "dd/MM/yy",
                java.util.Locale.getDefault()
            )
        formatoFecha.timeZone =
            java.util.TimeZone.getDefault()
        val fecha =
            formatoFecha.format(
                java.util.Date()
            )

        android.util.Log.d(
            "FECHA_DEBUG",
            fecha
        )

        prefs.edit()
            .putString(
                "mensajeAdmin",
                mensaje
            )
            .putString(
                "fechaMensajeAdmin",
                fecha
            )
            .apply()

        textMensajeAdminMensajes.text =
            mensaje
        textFechaActualizacion.text =
            "Actualizado: $fecha"
        android.widget.Toast.makeText(
            requireContext(),
            "Mensaje publicado",
            android.widget.Toast.LENGTH_SHORT
        ).show()
    }

    private fun cargarMensajeAdmin() {
        val prefs =
            requireContext().getSharedPreferences(
                "MindMoneyPrefs",
                android.content.Context.MODE_PRIVATE
            )
        val mensaje =
            prefs.getString(
                "mensajeAdmin",
                "Recuerda registrar tus gastos diarios para mantener un mejor control financiero."
            )
        val fecha =
            prefs.getString(
                "fechaMensajeAdmin",
                "--/--/--"
            )

        textMensajeAdminMensajes.text =
            mensaje
        textFechaActualizacion.text =
            "Actualizado: $fecha"
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment MensajesFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            MensajesFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}