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
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_register.*
import java.util.*

class LoginActivity : AppCompatActivity() {
    lateinit var mEmail : EditText
    lateinit var mPassword : EditText
    lateinit var progressBar : ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.NoActionBar)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        mEmail = findViewById(R.id.Email2)
        mPassword = findViewById(R.id.Password2)
        progressBar = findViewById(R.id.progressBar2)

        if(FirebaseAuth.getInstance().currentUser != null){
            val intent = Intent(this@LoginActivity, ScannerActivity::class.java)
            startActivity(intent)
        }

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