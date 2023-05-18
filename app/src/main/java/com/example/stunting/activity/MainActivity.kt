package com.example.stunting.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
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
        presenter = StuntingPresenterImpl(this, Retrofit.getInstance())

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

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                if (!recyclerView.canScrollVertically(2)) {
                    limit += 6
                    presenter.getStunting(sessionManager.getId(), limit, search)
                    itemCount += 6 //load 10 more items
                    adapter.updateItemCount(itemCount)

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
    }

    override fun onSuccess(msg: String) {

    }

    override fun onError(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    override fun SendData(data: List<Stunting>) {
        adapter = StuntingAdapter(itemCount, data, this, Retrofit.getInstance())
        this.recyclerView.adapter = adapter
    }

    override fun onDelete(){
        presenter.getStunting(sessionManager.getId(), limit, search)
    }
}