package com.santoni.digibiz

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_finalize_shopping.*

class FinalizeShoppingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_finalize_shopping)

        var calculateTotalPriceUrl = "http://192.168.100.11/Android/calculate_total_price.php?invoice_num=${intent.getStringExtra("LATEST_INVOICE_NUMBER")}"
        var requestQ = Volley.newRequestQueue(this@FinalizeShoppingActivity)
        var stringRequest = StringRequest(Request.Method.GET, calculateTotalPriceUrl, Response.Listener {
            response ->

            btn_paymentProcessing.text = "Pay $$response via Paypal Now!"


        }, Response.ErrorListener {
            error ->


        })


        requestQ.add(stringRequest)


    }
}
