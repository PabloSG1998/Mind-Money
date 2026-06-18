package com.mindmoney.ui.tareas

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mindmoney.R
import android.widget.ImageButton
import android.widget.TextView
import com.mindmoney.Tarea

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [TareasFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class TareasFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private lateinit var titulosTareas: List<TextView>
    private lateinit var botonesBorrar: List<ImageButton>
    private lateinit var editTituloTarea: android.widget.EditText
    private lateinit var editDescripcionTarea: android.widget.EditText
    private lateinit var buttonAgregarTarea: android.widget.Button
    private lateinit var textContadorTareas: TextView
    private lateinit var cardsTareas: List<androidx.cardview.widget.CardView>
    private val listaTareas = mutableListOf<Tarea>()
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
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tareas, container, false)
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(
            view,
            savedInstanceState
        )

        titulosTareas = listOf(
            view.findViewById(R.id.tituloTarea1),
            view.findViewById(R.id.tituloTarea2),
            view.findViewById(R.id.tituloTarea3),
            view.findViewById(R.id.tituloTarea4),
            view.findViewById(R.id.tituloTarea5),
            view.findViewById(R.id.tituloTarea6),
            view.findViewById(R.id.tituloTarea7),
            view.findViewById(R.id.tituloTarea8),
            view.findViewById(R.id.tituloTarea9),
            view.findViewById(R.id.tituloTarea10)
        )

        botonesBorrar = listOf(
            view.findViewById(R.id.borrarTarea1),
            view.findViewById(R.id.borrarTarea2),
            view.findViewById(R.id.borrarTarea3),
            view.findViewById(R.id.borrarTarea4),
            view.findViewById(R.id.borrarTarea5),
            view.findViewById(R.id.borrarTarea6),
            view.findViewById(R.id.borrarTarea7),
            view.findViewById(R.id.borrarTarea8),
            view.findViewById(R.id.borrarTarea9),
            view.findViewById(R.id.borrarTarea10)
        )

        cardsTareas = listOf(
            view.findViewById(R.id.cardTarea1),
            view.findViewById(R.id.cardTarea2),
            view.findViewById(R.id.cardTarea3),
            view.findViewById(R.id.cardTarea4),
            view.findViewById(R.id.cardTarea5),
            view.findViewById(R.id.cardTarea6),
            view.findViewById(R.id.cardTarea7),
            view.findViewById(R.id.cardTarea8),
            view.findViewById(R.id.cardTarea9),
            view.findViewById(R.id.cardTarea10)
        )

        editTituloTarea =
            view.findViewById(R.id.editTituloTarea)
        editDescripcionTarea =
            view.findViewById(R.id.editDescripcionTarea)
        buttonAgregarTarea =
            view.findViewById(R.id.buttonAgregarTarea)
        textContadorTareas =
            view.findViewById(R.id.textContadorTareas)

        for (i in botonesBorrar.indices) {
            botonesBorrar[i].setOnClickListener {
                if (i < listaTareas.size) {
                    androidx.appcompat.app.AlertDialog.Builder(requireContext())
                        .setTitle("Eliminar tarea")
                        .setMessage("¿Deseas eliminar esta tarea?")
                        .setPositiveButton("Eliminar") { _, _ ->
                            listaTareas.removeAt(i)
                            guardarTareas()
                            actualizarTareas()
                        }
                        .setNegativeButton("Cancelar", null)
                        .show()
                }
            }
        }

        for (i in cardsTareas.indices) {
            cardsTareas[i].setOnClickListener {
                if (i < listaTareas.size) {
                    androidx.appcompat.app.AlertDialog.Builder(requireContext())
                        .setTitle(listaTareas[i].titulo)
                        .setMessage(listaTareas[i].descripcion)
                        .setPositiveButton("Cerrar", null)
                        .show()
                }
            }
        }

        cargarTareas()
        actualizarTareas()
        buttonAgregarTarea.setOnClickListener {
            val titulo =
                editTituloTarea.text.toString().trim()
            val descripcion =
                editDescripcionTarea.text.toString().trim()

            if (titulo.isEmpty() || descripcion.isEmpty()) {
                android.widget.Toast.makeText(
                    requireContext(),
                    "Completa todos los campos",
                    android.widget.Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            if (listaTareas.size >= 10) {
                android.widget.Toast.makeText(
                    requireContext(),
                    "Máximo 10 tareas",
                    android.widget.Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            listaTareas.add(
                0,
                Tarea(
                    titulo,
                    descripcion
                )
            )
            guardarTareas()

            editTituloTarea.setText("")
            editDescripcionTarea.setText("")

            actualizarTareas()
        }
    }

    private fun actualizarTareas() {

        for (i in 0 until 10) {
            if (i < listaTareas.size) {
                titulosTareas[i].text =
                    listaTareas[i].titulo
                botonesBorrar[i].visibility =
                    View.VISIBLE
            } else {
                titulosTareas[i].text =
                    "Sin tareas pendientes"
                botonesBorrar[i].visibility =
                    View.GONE
            }
        }

        textContadorTareas.text =
            "Agregar nueva tarea (${listaTareas.size}/10)"
    }

    private fun guardarTareas() {
        val prefs = requireContext()
            .getSharedPreferences(
                "MindMoneyPrefs",
                android.content.Context.MODE_PRIVATE
            )
        val editor = prefs.edit()
        val texto =
            listaTareas.joinToString("|||") {
                "${it.titulo}###${it.descripcion}"
            }
        editor.putString(
            "tareas",
            texto
        )
        editor.apply()
    }

    private fun cargarTareas() {
        val prefs = requireContext()
            .getSharedPreferences(
                "MindMoneyPrefs",
                android.content.Context.MODE_PRIVATE
            )
        val texto =
            prefs.getString(
                "tareas",
                ""
            ) ?: ""
        listaTareas.clear()
        if (texto.isNotEmpty()) {
            val registros =
                texto.split("|||")
            for (registro in registros) {
                val partes =
                    registro.split("###")
                if (partes.size == 2) {
                    listaTareas.add(
                        Tarea(
                            partes[0],
                            partes[1]
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
         * @return A new instance of fragment TareasFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            TareasFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}