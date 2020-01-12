package com.najibun.nim175410171.AppBaret21

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import com.najibun.nim175410171.AppBaret21.R

import kotlinx.android.synthetic.main.activity_tentang.*
import kotlinx.android.synthetic.main.content_tentang.*

class TentangActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tentang)
        setSupportActionBar(toolbar)
        tv_link1.setOnClickListener {
            val i = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.instagram.com/baret21/"))
            startActivity(i)

        }
        tv_link2.setOnClickListener {
            val i = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/channel/UCIHbNaCq4yQoGI01UuAe92Q"))
            startActivity(i)

        }
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

}
