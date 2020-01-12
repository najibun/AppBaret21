package com.najibun.nim175410171.AppBaret21

import android.content.Intent
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import com.najibun.nim175410171.AppBaret21.R

import kotlinx.android.synthetic.main.activity_pengurus.*
import kotlinx.android.synthetic.main.content_pengurus.*

class Pengurus : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pengurus)
        setSupportActionBar(toolbar)
        tv_link.setOnClickListener {
            val i = Intent(this, TentangActivity::class.java)
            startActivity(i)

        }


        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

}
