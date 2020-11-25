package com.example.project

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.sdsmdg.harjot.vectormaster.VectorMasterView
import com.sdsmdg.harjot.vectormaster.models.PathModel

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [PressFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class PressFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    lateinit var db : DocumentReference
    lateinit var db2 : DocumentReference

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
        val view: View = inflater.inflate(R.layout.fragment_press, container, false)
        var pressPieDer: VectorMasterView = view.findViewById(R.id.pressPieDer)
        //var pressPieIzq: VectorMasterView = view.findViewById(R.id.pressPieIzq)

        db = FirebaseFirestore.getInstance().document("TTInsole/users")
        db2 = FirebaseFirestore.getInstance().document("TTInsole/micros")

        //Obtener el mc asociado al uid
        val ns = db.collection("${FirebaseAuth.getInstance().uid}").document("ns")
        ns.get().addOnSuccessListener { document ->
            if (document != null) {
                Log.d("TEST", "DocumentSnapshot data: ${document.data}")
                val nsd = document.getString("nsd")
                val nsi = document.getString("nsi")
                //Obtener el array de temperatura Derecha
                val pressd = db2.collection("ns").document("${nsd}").collection("${FirebaseAuth.getInstance().uid}").document("press")
                pressd.get()
                    .addOnSuccessListener { document ->
                        if (document != null){
                            val list_press = document["pder"] as ArrayList<Number>?
                            Log.d("PRUEBAMESTA", list_press.toString())

                            if (list_press != null) {
                                //Establecer rango de color segun el valor leido
                                var a = 0
                                for (p in list_press) {
                                    a = a + 1
                                    if(p.toInt() <= 2){
                                        //println(p)
                                        //println("rango 1")
                                    }
                                    if(p.toInt() in 3..7){
                                        var opd1: PathModel = pressPieDer.getPathModelByName("opd" + a)
                                        opd1.setFillColor(Color.parseColor("#fcba03"))
                                        //println(p)
                                        //println("rango 2")
                                    }
                                    if(p.toInt() > 7){
                                        var opd2: PathModel = pressPieDer.getPathModelByName("opd" + a)
                                        opd2.setFillColor(Color.parseColor("#ED4337"))
                                        //println(p)
                                        //println("rango 3")
                                    }
                                }
                                pressPieDer.update()
                            }
                            Log.d("PRUEBA", "DocumentSnapshot data: ${document.data}")
                        } else {
                            Log.d("PRUEBA", "No such document")
                        }
                    }
/*
                //Obtener el array de temperatura Izquierda
                val pressi = db2.collection("ns").document("${nsi}").collection("${FirebaseAuth.getInstance().uid}").document("press")
                pressi.get()
                    .addOnSuccessListener { document ->
                        if (document != null){
                            val list_press = document["pizq"] as ArrayList<Number>?
                            Log.d("PRUEBAMESTA", list_press.toString())

                            if (list_press != null) {
                                //Establecer rango de color segun el valor leido
                                var a = 0
                                for (p in list_press) {
                                    a = a + 1
                                    if(p.toInt() <= 15){
                                        println(p)
                                        println("rango 1")
                                    }
                                    if(p.toInt() in 16..20){
                                        var opi1: PathModel = pressPieIzq.getPathModelByName("opi" + a)
                                        opi1.setFillColor(Color.parseColor("#fcba03"))
                                        println(p)
                                        println("rango 2")
                                    }
                                    if(p.toInt() > 20){
                                        var opi2: PathModel = pressPieIzq.getPathModelByName("opi" + a)
                                        opi2.setFillColor(Color.parseColor("#ED4337"))
                                        println(p)
                                        println("rango 3")
                                    }
                                }
                                //Refrescar los colores de los vectores
                                pressPieIzq.update()
                                pressPieDer.update()
                            }
                            Log.d("PRUEBA", "DocumentSnapshot data: ${document.data}")
                        } else {
                            Log.d("PRUEBA", "No such document")
                        }
                    }
                    .addOnFailureListener { exception ->
                        Log.d("PRUEBA", "get failed with ", exception)
                    }
                */
            } else {
                Log.d("TEST", "No such document")
            }
        }
        return view

    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment PressFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            PressFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}