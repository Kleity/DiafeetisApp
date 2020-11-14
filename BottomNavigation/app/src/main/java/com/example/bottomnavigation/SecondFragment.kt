package com.example.bottomnavigation

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.sdsmdg.harjot.vectormaster.VectorMasterView
import com.sdsmdg.harjot.vectormaster.models.PathModel

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SecondFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SecondFragment : Fragment() {
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

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_second, container, false)
        var heartVector: VectorMasterView = view.findViewById(R.id.heart_vector)
        var btnChange: Button = view.findViewById(R.id.btnChange)

        var outline1: PathModel = heartVector.getPathModelByName("outline1")
        var outline2: PathModel = heartVector.getPathModelByName("outline2")
        var outline3: PathModel = heartVector.getPathModelByName("outline3")
        var outline4: PathModel = heartVector.getPathModelByName("outline4")
        var outline5: PathModel = heartVector.getPathModelByName("outline5")
        var outline6: PathModel = heartVector.getPathModelByName("outline6")
        var outline7: PathModel = heartVector.getPathModelByName("outline7")

        btnChange.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?){
                //codigo
                Log.d("MIO","Entro")
                outline1.setFillColor(Color.parseColor("#ED4337"))
                heartVector.update()
            }
        })

        return view
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment SecondFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SecondFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}