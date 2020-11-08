package com.example.project

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.DocumentReference


class RegisterActivity : AppCompatActivity() {
    lateinit var mEmail : EditText
    lateinit var mPersonName : EditText
    lateinit var mPassword : EditText
    lateinit var btnRegistrate : Button
    lateinit var progressBar : ProgressBar
    lateinit var db : DocumentReference

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.NoActionBar)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        mPersonName = findViewById(R.id.PersonName)
        mEmail = findViewById(R.id.Email)
        mPassword = findViewById(R.id.Password)
        progressBar = findViewById(R.id.progressBar)
        btnRegistrate = findViewById(R.id.btnRegistrate)
        db = FirebaseFirestore.getInstance().document("TTInsole/users")

        btnRegistrate.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                var email = mEmail.getText().toString().trim()
                var password = mPassword.getText().toString().trim()
                var nombre = mPersonName.getText().toString().trim()

                // Verificacion de campos
                if(TextUtils.isEmpty(nombre)) {
                    mPersonName.setError("El campo Nombre es obligatorio")
                    return
                }
                if(TextUtils.isEmpty(email)){
                    mEmail.setError("El campo Correo es obligatorio")
                    return
                }
                if(password.length < 6){
                    mPassword.setError("La contraseña debe tener más de 6 caracteres")
                    return
                }

                progressBar.setVisibility(View.VISIBLE)

                 // Autenticacion de usuario
                 FirebaseAuth.getInstance().createUserWithEmailAndPassword(email,password).addOnCompleteListener{ task ->
                     if(task.isSuccessful){
                         Toast.makeText(applicationContext, "Usuario Creado", Toast.LENGTH_SHORT).show()
                         Log.d("TEST", "usuario creado")

                         // Guardar datos del usuario
                         val userData = hashMapOf<String, Any>(
                             "name" to nombre,
                             "email" to email
                         )
                         db.collection("${FirebaseAuth.getInstance().uid}").document("uInfo").set(userData)
                             .addOnSuccessListener {
                                     void: Void? -> Toast.makeText(applicationContext, "Successfully uploaded to the database :)", Toast.LENGTH_LONG).show()
                             }.addOnFailureListener{
                                     exception: java.lang.Exception -> Toast.makeText(applicationContext, exception.toString(), Toast.LENGTH_LONG).show()
                                     Log.d("TEST", "${exception.toString()}")
                             }

                         val intent = Intent(this@RegisterActivity, ScannerActivity::class.java)
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
    fun backToLogin(view: View) {
        finish()
    }
}