package com.santoni.digibiz

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AlertDialog
import android.view.Menu
import android.widget.ArrayAdapter
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_home_screen.*

class HomeScreen : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_screen)

        val brandsURL = "http://192.168.100.11/Android/fetch_brands.php"

        var brandsList = ArrayList<String>()

        var requestQ = Volley.newRequestQueue(this@HomeScreen)

        var jsonAR = JsonArrayRequest(Request.Method.GET, brandsURL, null, Response.Listener {
            response ->

            for (jsonObject in 0.until(response.length())) {

                brandsList.add(response.getJSONObject(jsonObject).getString("brand"))

            }

            var brandsListAdapter = ArrayAdapter(this@HomeScreen, R.layout.brand_item_text_view, brandsList)
            brandsListView.adapter = brandsListAdapter


        }, Response.ErrorListener {  error ->

            val dialogBuilder = AlertDialog.Builder(this)
            dialogBuilder.setTitle("Alert")
            dialogBuilder.setMessage(error.message)
            dialogBuilder.create().show()
        })

        requestQ.add(jsonAR)


        brandsListView.setOnItemClickListener { adapterView, view, i, l ->

            val tappedBrand = brandsList.get(i)

            val intent = Intent(this@HomeScreen, FetchEProductsActivity::class.java)

            intent.putExtra("BRAND", tappedBrand)
            startActivity(intent)





        }


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {


        menuInflater.inflate(R.menu.account_menu, menu)

        return super.onCreateOptionsMenu(menu)

    }

    private var doubleBackToExitPressedOnce = false
    override fun onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed()
            return
        }

        this.doubleBackToExitPressedOnce = true
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show()

        Handler().postDelayed(Runnable { doubleBackToExitPressedOnce = false }, 2000)
    }
}
