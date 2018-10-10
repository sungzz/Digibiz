package com.santoni.digibiz

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_cart_products.*

class CartProductsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart_products)

        var cartProductsURL = "http://192.168.100.11/Android/fetch_temporary_order.php?email=${Person.email}"
        var cartProductsList = ArrayList<String>()
        var requestQ = Volley.newRequestQueue(this@CartProductsActivity)
        var jsonAR = JsonArrayRequest(Request.Method.GET, cartProductsURL, null, Response.Listener {
            response ->


            for (joIndex in 0.until(response.length())){

                cartProductsList.add("${response.getJSONObject(joIndex).getInt("id")}" +
                        " \n ${response.getJSONObject(joIndex).getString("name")}" +
                        " \n ${response.getJSONObject(joIndex).getInt("price")}" +
                        " \n ${response.getJSONObject(joIndex).getString("email")}")

            }

            var cartProductsAdapter = ArrayAdapter(this@CartProductsActivity, android.R.layout.simple_list_item_1, cartProductsList)
            cartProductsListView.adapter = cartProductsAdapter


        }, Response.ErrorListener {
            error ->


        })

        requestQ.add(jsonAR)


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {


        menuInflater.inflate(R.menu.cart_menu, menu)

        return super.onCreateOptionsMenu(menu)

    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        if (item?.itemId == R.id.continueShoppingItem) {

            var intent = Intent(this@CartProductsActivity, HomeScreen::class.java)
            startActivity(intent)

            this.finish()
        } else if (item?.itemId == R.id.declineOrderItem) {

            val deleteUrl = "http://192.168.100.11/Android/decline_order.php?email=${Person.email}"
            val requesQ = Volley.newRequestQueue(this@CartProductsActivity)
            val stringReuqst = StringRequest(Request.Method.GET, deleteUrl, Response.Listener {
                response ->

                var intent = Intent(this, HomeScreen::class.java)
                startActivity(intent)

                this.finish()

            }, Response.ErrorListener {
                error ->


            })

            requesQ.add(stringReuqst)

        } else if (item?.itemId == R.id.verifyOrderItem) {

            val verifyURL = "http://192.168.100.11/Android/verify_order.php?email=${Person.email}"
            val requesQ = Volley.newRequestQueue(this@CartProductsActivity)
            val stringRequest = StringRequest(Request.Method.GET, verifyURL, Response.Listener {
                response ->

                var intent = Intent(this, FinalizeShoppingActivity::class.java)
                Toast.makeText(this, response, Toast.LENGTH_LONG).show()
                intent.putExtra("LATEST_INVOICE_NUMBER", response)
                startActivity(intent)

                this.finish()

            }, Response.ErrorListener {
                error ->


            })

            requesQ.add(stringRequest)


        }



        return super.onOptionsItemSelected(item)
    }


}
