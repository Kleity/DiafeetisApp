package com.example.project

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.google.zxing.integration.android.IntentIntegrator
import kotlinx.android.synthetic.main.activity_scanner.*


class ScannerActivity : AppCompatActivity() {
    lateinit var db : DocumentReference
    lateinit var db2 : DocumentReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scanner)
        db = FirebaseFirestore.getInstance().document("TTInsole/users")
        db2 = FirebaseFirestore.getInstance().document("TTInsole/micros")

        btnScan.setOnClickListener{
            val scanner =  IntentIntegrator(this)
            scanner.initiateScan()
        }
    }
    //Escaneo y almacenamiento de plantillas
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(resultCode == Activity.RESULT_OK){
            val result =
                IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
            if (result != null) {
                if (result.contents == null) {
                    Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(this, "Scanned: " + result.contents, Toast.LENGTH_LONG)
                        .show()
                    val int1: Int? = result.contents.toInt()
                    val int2 = int1?.plus(1)
                    val numSerie = hashMapOf<String, Any>(
                        "nsd" to "mc" + int1,
                        "nsi" to "mc" + int2
                    )

                    db2.collection("ns").document("mc${int1}").collection("${FirebaseAuth.getInstance().uid}").document("datos").set(numSerie)
                    db2.collection("ns").document("mc${int2}").collection("${FirebaseAuth.getInstance().uid}").document("datos").set(numSerie)
                    db.collection("${FirebaseAuth.getInstance().uid}").document("ns").set(numSerie)

                    val intent = Intent(this@ScannerActivity, MainActivity::class.java)
                    startActivity(intent)

                }
            } else {
                super.onActivityResult(requestCode, resultCode, data)
            }
        }
    }
    // Cerrar sesión
    fun signout(view: View) {
        FirebaseAuth.getInstance().signOut()
        val intent = Intent(this@ScannerActivity, LoginActivity::class.java)
        startActivity(intent)
    }
}