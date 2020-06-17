package com.example.bkfoodcourt

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkInfo
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /** Call function to check network connection on start up **/
        if (!checkNetwork(this)) {
            Toast.makeText(this, "Please connect to network", Toast.LENGTH_LONG).show() /** If the mobile is not connected to network, show an notification**/
        }
    }

    var mAuth = FirebaseAuth.getInstance() /** Create an authentication instance **/

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
                    Toast.makeText(this, "Wrong email/password", Toast.LENGTH_SHORT).show() /** Show the notification that logged failed **/
                }
            })

        }else {
            Toast.makeText(this, "Please enter email/password", Toast.LENGTH_SHORT).show() /** Show the notification about blank input **/
        }
    }

    /******************************************************/

    /** Check network function **/

    /******************************************************/

   fun checkNetwork (context: Context) : Boolean{
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = cm.activeNetworkInfo
        val isConnected: Boolean = activeNetwork?.isConnectedOrConnecting == true
        return isConnected
    }

    /******************************************************/
}
