package com.santoni.digibiz

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var EMPTY = "";
    private var ERROR_INPUT_EMPTY = "Please fill all fields";
    private var SAVED = "Saved!";
    private var myPreferences = "myPrefs"
    private var PASSWORD = "passWord";
    private var EMAIL = "email";

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val sharedPreferences = getSharedPreferences(myPreferences, Context.MODE_PRIVATE)


        if (sharedPreferences.getString(EMAIL, EMPTY) != EMPTY) {
            val homeIntent = Intent(this@MainActivity,HomeScreen::class.java)
            startActivity(homeIntent)

            this.finish()

//            val email = sharedPreferences.getString(EMAIL, EMPTY)
//            val passWord = sharedPreferences.getString(PASSWORD, EMPTY)




        } else {

            activity_main_btnLogin.setOnClickListener {

                if (activity_main_edt_loginEmail.text.toString() == EMPTY
                        || activity_main_edt_loginPassword.text.toString() == EMPTY) {
                    Toast.makeText(applicationContext, ERROR_INPUT_EMPTY, Toast.LENGTH_LONG).show()

                } else {
                    //If all fields are filled then fetch the data and
                    // save the data in Shared Preferences
                    val editor = sharedPreferences.edit()
                    editor.putString(EMAIL, activity_main_edt_loginEmail.text.toString())
                    editor.putString(PASSWORD, activity_main_edt_loginPassword.text.toString())
                    editor.apply()

                    val loginURL = "http://192.168.100.11/Android/login_app_user.php?email=" +
                            activity_main_edt_loginEmail.text.toString() + "&pass=" +
                            activity_main_edt_loginPassword.text.toString()

                    val requestQ = Volley.newRequestQueue(this@MainActivity)
                    val stringRequest = StringRequest(Request.Method.GET, loginURL, Response.Listener {
                        response ->

                        if (response.equals("The user does exist")) {

                            Person.email = activity_main_edt_loginEmail.text.toString()
                            Toast.makeText(this@MainActivity, response, Toast.LENGTH_SHORT).show()
                            val homeIntent = Intent(this@MainActivity,HomeScreen::class.java)
                            startActivity(homeIntent)

                            //Clear the text boxes and show Toast message
                            activity_main_edt_loginEmail.setText(EMPTY)
                            activity_main_edt_loginPassword.setText(EMPTY)
                            Toast.makeText(applicationContext, SAVED, Toast.LENGTH_LONG).show()

                        } else {

                            val dialogBuilder = AlertDialog.Builder(this)
                            dialogBuilder.setTitle("Alert")
                            dialogBuilder.setMessage(response)
                            dialogBuilder.create().show()
                        }


                    }, Response.ErrorListener { error ->

                        val dialogBuilder = AlertDialog.Builder(this)
                        dialogBuilder.setTitle("Alert")
                        dialogBuilder.setMessage(error.message)
                        dialogBuilder.create().show()

                    })

                    requestQ.add(stringRequest)

                }

            }


        }





        activity_main_btnLoginSignUp.setOnClickListener {

            var signUpIntent = Intent(this@MainActivity, SignUpLayout::class.java)
            startActivity(signUpIntent)


        }

    }
}
