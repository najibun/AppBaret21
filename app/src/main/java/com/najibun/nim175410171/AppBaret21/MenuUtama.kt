package com.najibun.nim175410171.AppBaret21

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import kotlinx.android.synthetic.main.activity_menu_utama.*

class MenuUtama : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu_utama)
        btn_acara.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)

        }
        btn_urus.setOnClickListener {
            val intent = Intent(this, Pengurus::class.java)
            startActivity(intent)

        }
        btn_tentang.setOnClickListener {
            val intent = Intent(this, TentangActivity::class.java)
            startActivity(intent)

        }
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.about_menu, menu)
        return true
    }

}
