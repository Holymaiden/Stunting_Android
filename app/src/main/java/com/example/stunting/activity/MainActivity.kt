package com.example.stunting.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.stunting.R
import com.example.stunting.adapter.StuntingAdapter
import com.example.stunting.presenter.StuntingPresenterImpl
import com.example.stunting.view.StuntingView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.example.stunting.network.Retrofit
import com.example.stunting.response.Stunting
import com.example.stunting.session.SessionManager
import com.example.stunting.network.NetworkChangeReceiver

class MainActivity : AppCompatActivity(), StuntingView {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: StuntingAdapter
    private var itemCount = 0
    private lateinit var presenter: StuntingPresenterImpl
    private lateinit var sessionManager: SessionManager
    var limit: Int = 6
    var search: String = ""

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // membuat instance dari SessionManager
        sessionManager = SessionManager(this)
        presenter = StuntingPresenterImpl(this, Retrofit.getInstance(), this)

        val btnProfile = findViewById<LinearLayout>(R.id.ll_profile)
        val btnStunting = findViewById<FloatingActionButton>(R.id.btnStunting)
        this.recyclerView = findViewById(R.id.recyclerView)
        val textSearch = findViewById<android.widget.EditText>(R.id.editTextTextPersonName)

        btnProfile.setOnClickListener {
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
        }

        btnStunting.setOnClickListener {
            val intent = Intent(this, StuntingActivity::class.java)
            startActivity(intent)
        }

        presenter.getStunting(sessionManager.getId(), limit, search)

        var isLoading = false
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val layoutManager = recyclerView.layoutManager
                val visibleItemCount = layoutManager?.childCount ?: 0
                val totalItemCount = layoutManager?.itemCount ?: 0
                val firstVisibleItemPosition = (layoutManager as? LinearLayoutManager)?.findFirstVisibleItemPosition() ?: 0

                val loadMoreThreshold = 6 // ambil jumlah item threshold yang ingin Anda gunakan

                if (!isLoading && visibleItemCount + firstVisibleItemPosition + loadMoreThreshold >= totalItemCount) {
                    // Jika tidak ada proses loading dan pengguna sudah mendekati akhir daftar

                    isLoading = true // set isLoading menjadi true untuk menghindari multiple loading

                    limit += 6
                    presenter.getStunting(sessionManager.getId(), limit, search)
                }
            }
        })

        textSearch.setOnEditorActionListener { v, _, event ->
            search = textSearch.text.toString()
            presenter.getStunting(sessionManager.getId(), limit, search)
            true
        }

        textSearch.setOnTouchListener { _, event ->
            if (event.action == android.view.MotionEvent.ACTION_UP) {
                if (event.rawX >= (textSearch.right - textSearch.compoundDrawables[2].bounds.width())) {
                    search = textSearch.text.toString()
                    presenter.getStunting(sessionManager.getId(), limit, search)
                }
            }
            false
        }

        // Menginisialisasi NetworkChangeReceiver
        val networkChangeReceiver = NetworkChangeReceiver()
        // Mendaftarkan NetworkChangeReceiver ke sistem
        networkChangeReceiver.onReceive(this, Intent())

    }

    override fun onSuccess(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    override fun onError(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    override fun SendData(data: List<Stunting>) {
        try{
            adapter = StuntingAdapter(itemCount, data, this, Retrofit.getInstance())
            this.recyclerView.adapter = adapter
        }catch (e: Exception){
            Log.d("Error", e.toString())
        }

    }

    override fun onDelete(){
        presenter.getStunting(sessionManager.getId(), limit, search)
    }
}