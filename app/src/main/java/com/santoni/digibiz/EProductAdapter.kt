package com.santoni.digibiz

import android.app.Activity
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.e_product_row.view.*

class EProductAdapter(var context: Context, var arrayList: ArrayList<EProduct>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        val productView = LayoutInflater.from(context).inflate(R.layout.e_product_row, parent, false)

        return ProductViewHolder(productView)
    }

    override fun getItemCount(): Int {

        return arrayList.size

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        // arraylist below get from EProduct class model
        (holder as ProductViewHolder).initializeRowUIComponents(
                arrayList.get(position).id,
                arrayList.get(position).name,
                arrayList.get(position).price,
                arrayList.get(position).picture)

    }


    // inner class below create for give the value to EproductAdapter class, and when create below code also
    // need to create new layout and design it example : layout -> e_product_row.xml

    inner class ProductViewHolder(pView: View) : RecyclerView.ViewHolder(pView) {


        fun initializeRowUIComponents(id: Int, name: String, price: Int, picName: String) {

            itemView.e_product_row_txtId.text = id.toString()
            itemView.e_product_row_txtName.text = name
            itemView.e_product_row_txtPrice.text = price.toString()

            var picUrl = "http://192.168.100.11/Android/osimages/"
            picUrl = picUrl.replace(" ", "%20")
            Picasso.get().load(picUrl + picName).into(itemView.e_product_row_imgProduct)

            // below for add product to temporary activity or mean to shopping cart

            itemView.e_product_row_imgAdd.setOnClickListener {

                //track id product to cart ( based Person class )
                Person.addToCartProductID = id
                ////////////


                var amountFragment = AmountFragment()
                var fragmentManager = (itemView.context as Activity).fragmentManager

                amountFragment.show(fragmentManager, "TAG")
            }

        }

    }

}