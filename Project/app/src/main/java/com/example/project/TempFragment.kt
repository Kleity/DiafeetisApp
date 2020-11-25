package com.example.project

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
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
 * Use the [TempFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class TempFragment : Fragment() {
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
        val view: View = inflater.inflate(R.layout.fragment_temp, container, false)
        var tempPieDer: VectorMasterView = view.findViewById(R.id.tempPieDer)
        var tempPieIzq: VectorMasterView = view.findViewById(R.id.tempPieIzq)

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
                val tempd = db2.collection("ns").document("${nsd}").collection("${FirebaseAuth.getInstance().uid}").document("temp")
                tempd.get()
                    .addOnSuccessListener { document ->
                        if (document != null){
                            val list_temp = document["tder"] as ArrayList<Number>?
                            Log.d("PRUEBAMESTA", list_temp.toString())

                            if (list_temp != null) {
                                //Establecer rango de color segun el valor leido
                                var a = 0
                                for (t in list_temp) {
                                    a = a + 1
                                    if(t.toInt() <= 15){
                                        println(t)
                                        println("rango 1")
                                    }
                                    if(t.toInt() in 16..20){
                                        var old1: PathModel = tempPieDer.getPathModelByName("old" + a)
                                        old1.setFillColor(Color.parseColor("#fcba03"))
                                        println(t)
                                        println("rango 2")
                                    }
                                    if(t.toInt() > 20){
                                        var old2: PathModel = tempPieDer.getPathModelByName("old" + a)
                                        old2.setFillColor(Color.parseColor("#ED4337"))
                                        println(t)
                                        println("rango 3")
                                    }
                                }

                            }
                            Log.d("PRUEBA", "DocumentSnapshot data: ${document.data}")
                        } else {
                            Log.d("PRUEBA", "No such document")
                        }
                    }

                //Obtener el array de temperatura Izquierda
                val tempi = db2.collection("ns").document("${nsi}").collection("${FirebaseAuth.getInstance().uid}").document("temp")
                tempi.get()
                    .addOnSuccessListener { document ->
                        if (document != null){
                            val list_temp = document["tizq"] as ArrayList<Number>?
                            Log.d("PRUEBAMESTA", list_temp.toString())

                            if (list_temp != null) {
                                //Establecer rango de color segun el valor leido
                                var a = 0
                                for (t in list_temp) {
                                    a = a + 1
                                    if(t.toInt() <= 15){
                                        println(t)
                                        println("rango 1")
                                    }
                                    if(t.toInt() in 16..20){
                                        var oli1: PathModel = tempPieIzq.getPathModelByName("oli" + a)
                                        oli1.setFillColor(Color.parseColor("#fcba03"))
                                        println(t)
                                        println("rango 2")
                                    }
                                    if(t.toInt() > 20){
                                        var oli2: PathModel = tempPieIzq.getPathModelByName("oli" + a)
                                        oli2.setFillColor(Color.parseColor("#ED4337"))
                                        println(t)
                                        println("rango 3")
                                    }
                                }
                                //Refrescar los colores de los vectores
                                tempPieIzq.update()
                                tempPieDer.update()
                            }
                            Log.d("PRUEBA", "DocumentSnapshot data: ${document.data}")
                        } else {
                            Log.d("PRUEBA", "No such document")
                        }
                    }
                    .addOnFailureListener { exception ->
                        Log.d("PRUEBA", "get failed with ", exception)
                    }
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
         * @return A new instance of fragment TempFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            TempFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
