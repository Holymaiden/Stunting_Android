package com.example.stunting.adapter

import android.util.Log
import androidx.appcompat.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.stunting.R
import com.example.stunting.presenter.StuntingPresenterImpl
import com.example.stunting.response.Stunting
import com.example.stunting.view.StuntingView
import retrofit2.Retrofit

class StuntingAdapter(private var itemCount: Int, private var listData: List<Stunting>, private val view: StuntingView): RecyclerView.Adapter<StuntingAdapter.MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.holder_view, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val data = listData[position]
        holder.bind(data, view)
    }

    override fun getItemCount(): Int {
        return listData.size
    }

    fun updateItemCount(count: Int) {
        itemCount = count
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nama: TextView = itemView.findViewById(R.id.textNama)
        private val umur: TextView = itemView.findViewById(R.id.textUmur)
        private val berat: TextView = itemView.findViewById(R.id.textBerat)
        private val tinggi: TextView = itemView.findViewById(R.id.textTinggi)
        private val stunting: TextView = itemView.findViewById(R.id.textStunting)
        private val gambar: ImageView = itemView.findViewById(R.id.imageStunting)
        private val btnHapus: ImageView = itemView.findViewById(R.id.btn_trash)

        fun bind(data: Stunting, view: StuntingView) {
            nama.text = data.username
            umur.text = "Umur : "+data.umur.toString()
            berat.text = "BB : "+data.berat_badan.toString()
            tinggi.text = "TB : "+data.tinggi_badan.toString()      
            stunting.text = data.stunting
            if (data.stunting == "Ya") {
                val drawable = itemView.resources.getDrawable(R.drawable.ic_check)
                drawable.setTint(itemView.resources.getColor(R.color.colorGreen))
                gambar.setImageDrawable(drawable)
            } else {
                val drawable = itemView.resources.getDrawable(R.drawable.ic_err)
                drawable.setTint(itemView.resources.getColor(R.color.colorWarning))
                gambar.setImageDrawable(drawable)
            }

            btnHapus.setOnClickListener {
                val viewDialog = View.inflate( itemView.context, R.layout.dialog_view_confirm, null)
                val builder = AlertDialog.Builder(itemView.context)
                builder.setView(viewDialog)
                builder.setCancelable(true)
                val dialog = builder.create()
                dialog.show()
                dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
                val btnOk = viewDialog.findViewById<Button>(R.id.btn_confirm)
                val btnCancel = viewDialog.findViewById<Button>(R.id.btn_confirm2)
                btnOk.setOnClickListener {
                    dialog.dismiss()
                    val presenter = StuntingPresenterImpl(view, viewDialog.context)
                    presenter.deleteStunting(data.id)
                }
                btnCancel.setOnClickListener {
                    dialog.dismiss()
                }
            }
        }
    }
}