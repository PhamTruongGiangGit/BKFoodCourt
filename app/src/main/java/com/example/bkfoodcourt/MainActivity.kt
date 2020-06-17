package com.example.bkfoodcourt

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    var mAuth = FirebaseAuth.getInstance() // Create an authentication instance

    /** Login function (activated when clicking login button) **/

    /******************************************************/
    fun login (view: View) {

        val emailTxt = findViewById<EditText>(R.id.editTextTextEmailAddress) as EditText /** Get the content of the input box of email **/
        var email = emailTxt.text.toString() /** Convert to string **/
        val passwordTxt = findViewById<EditText>(R.id.editTextTextPassword) as EditText /** Get the content of the input box of password **/
        var password = passwordTxt.text.toString() /** Convert to string **/

        if (!email.isEmpty() && !password.isEmpty()) {
            this.mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener ( this, OnCompleteListener<AuthResult> { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "Login Successfully", Toast.LENGTH_LONG).show() /** Show the notification that logged in successfully **/
                    startActivity(Intent(this, HomePage::class.java)) /** START A NEW ACTIVITY (MOVE TO HOME PAGE) **/
                } else {
                    Toast.makeText(this, "Login failed", Toast.LENGTH_SHORT).show() /** Show the notification that logged failed **/
                }
            })

        }else {
            Toast.makeText(this, "Please enter email/password", Toast.LENGTH_SHORT).show() /** Show the notification about blank input **/
        }
    }

    /*******************************************************/
}
