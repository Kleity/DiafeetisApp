package com.example.project

import android.app.Activity
import android.content.Intent
import android.os.Bundle
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
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scanner)
        db = FirebaseFirestore.getInstance().document("TTInsole/users")

        btnScan.setOnClickListener{
            val scanner =  IntentIntegrator(this)
            scanner.initiateScan()
        }
    }

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

                    val numSerie = hashMapOf<String, Any>(
                        "ns" to result.contents
                    )
                    db.collection("${FirebaseAuth.getInstance().uid}").document("uInfo").set(numSerie, SetOptions.merge())
                }
            } else {
                super.onActivityResult(requestCode, resultCode, data)
            }
        }
    }
    fun signout(view: View) {
        FirebaseAuth.getInstance().signOut()
        val intent = Intent(this@ScannerActivity, LoginActivity::class.java)
        startActivity(intent)
    }
}