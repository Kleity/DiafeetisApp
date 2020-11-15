package com.example.project

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_register.*
import java.util.*

class LoginActivity : AppCompatActivity() {
    lateinit var mEmail : EditText
    lateinit var mPassword : EditText
    lateinit var progressBar : ProgressBar
    lateinit var db : DocumentReference

    override fun onCreate(savedInstanceState: Bundle?) {
        if(FirebaseAuth.getInstance().currentUser != null){
            db = FirebaseFirestore.getInstance().document("TTInsole/users")
            val nsRef = db.collection("${FirebaseAuth.getInstance().uid}").document("ns")
            nsRef.get().addOnSuccessListener { document ->
                if (document.data != null) {
                    Log.d("TEST", "DocumentSnapshot data: ${document.data}")
                    val intent = Intent(this@LoginActivity, MainActivity::class.java)
                    startActivity(intent)
                } else {
                    Log.d("TEST", "No such document")
                    val intent = Intent(this@LoginActivity, ScannerActivity::class.java)
                    startActivity(intent)
                }
            }
                .addOnFailureListener { exception ->
                    Log.d("TEST", "get failed with ", exception)
                }
        }

        setTheme(R.style.NoActionBar)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        mEmail = findViewById(R.id.Email2)
        mPassword = findViewById(R.id.Password2)
        progressBar = findViewById(R.id.progressBar2)



        btnAcceder.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                var email = mEmail.getText().toString().trim()
                var password = mPassword.getText().toString().trim()

                // Verificacion de campos
                if(TextUtils.isEmpty(email)){
                    mEmail.setError("El Email es requerido")
                    return
                }
                if(TextUtils.isEmpty(password)){
                    mPassword.setError("La contraseña es requerida")
                    return
                }

                progressBar.setVisibility(View.VISIBLE)

                // Autenticacion de usuario
                FirebaseAuth.getInstance().signInWithEmailAndPassword(email,password).addOnCompleteListener{ task ->
                    if(task.isSuccessful){
                        Toast.makeText(applicationContext, "Logged in Successfully", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this@LoginActivity, ScannerActivity::class.java)
                        startActivity(intent)
                    }else{
                        Log.d("TEST", "createUserWithEmail:failure", task.exception)
                        Toast.makeText(applicationContext, "Error ! " + task.exception, Toast.LENGTH_SHORT).show()
                        progressBar.setVisibility(View.GONE)
                    }
                }
            }
        })
    }
    fun registrate(view: View) {
        Toast.makeText(this, "Pulsado", Toast.LENGTH_SHORT).show()
        val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
        startActivity(intent)
    }
}