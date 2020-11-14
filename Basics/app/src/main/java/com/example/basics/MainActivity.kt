package com.example.basics

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.sdsmdg.harjot.vectormaster.VectorMasterView
import com.sdsmdg.harjot.vectormaster.models.PathModel
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var heartVector: VectorMasterView = findViewById(R.id.heart_vector)
        var outline1: PathModel = heartVector.getPathModelByName("outline1")
        var outline2: PathModel = heartVector.getPathModelByName("outline2")
        var outline3: PathModel = heartVector.getPathModelByName("outline3")
        var outline4: PathModel = heartVector.getPathModelByName("outline4")
        var outline5: PathModel = heartVector.getPathModelByName("outline5")
        var outline6: PathModel = heartVector.getPathModelByName("outline6")
        var outline7: PathModel = heartVector.getPathModelByName("outline7")
        var btnChange: Button = findViewById(R.id.btnChange)

        btnChange.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?){
                //codigo
                Log.d("MIO","Entro")
                outline1.setFillColor(Color.parseColor("#ED4337"))
                heartVector.update()
            }
        })

        btnAll.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?){
                //codigo
                Log.d("MIO","Entro")
                outline1.setFillColor(Color.parseColor("#ED4337"))
                outline2.setFillColor(Color.parseColor("#ED4337"))
                outline3.setFillColor(Color.parseColor("#ED4337"))
                outline4.setFillColor(Color.parseColor("#ED4337"))
                outline5.setFillColor(Color.parseColor("#ED4337"))
                outline6.setFillColor(Color.parseColor("#ED4337"))
                outline7.setFillColor(Color.parseColor("#ED4337"))
                heartVector.update()
            }
        })
    }
}