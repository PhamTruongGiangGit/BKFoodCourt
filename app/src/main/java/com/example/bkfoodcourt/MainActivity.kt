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
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    val RC_SIGN_IN = 1000
    var googleSignInClient : GoogleSignInClient? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Configure Google Sign In
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)
        loginWithGoogleButton.setOnClickListener {
            var signInIntent = googleSignInClient?.signInIntent
            startActivityForResult(signInIntent, RC_SIGN_IN)
        }

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
                    startActivity(Intent(this, HomeActivity::class.java)) /** START A NEW ACTIVITY (MOVE TO HOME PAGE) **/
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

    fun firebaseAuthWithGoogle(acct : GoogleSignInAccount?){
        var credential = GoogleAuthProvider.getCredential(acct?.idToken, null)
        FirebaseAuth.getInstance().signInWithCredential(credential).addOnCompleteListener {
                task ->
            if (task.isSuccessful) {
                Toast.makeText(this, "Google login success!", Toast.LENGTH_LONG).show()
                val homeIntent = Intent(this, HomeActivity::class.java)
                startActivity(homeIntent)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN){
            var task = GoogleSignIn.getSignedInAccountFromIntent(data)
            var account = task.getResult(ApiException::class.java)
            firebaseAuthWithGoogle(account)
        }
    }
}
