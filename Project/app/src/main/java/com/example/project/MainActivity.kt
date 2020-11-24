package com.example.project

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore

class MainActivity : AppCompatActivity() {
    lateinit var db: DocumentReference
    lateinit var usernameWelcome: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        val navController = findNavController(R.id.fragment)

        bottomNavigationView.setupWithNavController(navController)

        db = FirebaseFirestore.getInstance().document("TTInsole/users")
        usernameWelcome = findViewById(R.id.bienvenido)

//Mensaje de bienvenida para usuario
        val username = db.collection("${FirebaseAuth.getInstance().uid}").document("uInfo")
        username.get().addOnSuccessListener { document ->
            if (document != null) {
                Log.d("WEA", "DocumentSnapshot data: ${document.data}")
                val nombre = document.getString("name")
                usernameWelcome.text = "Bienvenido $nombre"
            } else {
                Log.d("WEA", "No such document")
            }
        }
    }
}