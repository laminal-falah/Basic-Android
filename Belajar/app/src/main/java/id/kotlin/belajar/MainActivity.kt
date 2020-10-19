package id.kotlin.belajar

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.google.android.material.snackbar.Snackbar

/*
 * Copyright (C) 2020 the original author laminalfalah All Right Reserved.
 * 
 * This is part of the Belajar.
 * 
 * id.kotlin.belajar
 *
 * Licensed under the Apache License, Version 2.0 (the "License").
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/**
 * Created by laminalfalah on 16/10/20
 */

class MainActivity: AppCompatActivity() {

    private val clz = this.toString()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(clz, "OnCreate")
        setContentView(R.layout.activity_main)

        val btn = findViewById<MaterialButton>(R.id.btn_click)

        btn.setOnClickListener {
            Toast.makeText(this, "Hello", Toast.LENGTH_SHORT).show()
        }

        val root = findViewById<LinearLayout>(R.id.root)

        val snackBtn = findViewById<MaterialButton>(R.id.btn_show_snack_bar)

        snackBtn.setOnClickListener {
            Snackbar.make(root, "Snack Bar", Snackbar.LENGTH_SHORT).show()
        }

        supportFragmentManager.beginTransaction().replace(R.id.fl_main, MainFragment()).commit()

        val btnDetail = findViewById<MaterialButton>(R.id.btn_move_detail)

        btnDetail.setOnClickListener {
            startActivity(Intent(this, DetailActivity::class.java))
        }

        val btnList = findViewById<MaterialButton>(R.id.btn_move_list)

        btnList.setOnClickListener {
            startActivity(Intent(this, ListActivity::class.java))
        }

        val btnCustom = findViewById<MaterialButton>(R.id.btn_move_custom)

        btnCustom.setOnClickListener {
            startActivity(Intent(this, CustomActivity::class.java))
        }

    }

    override fun onPause() {
        super.onPause()
        Log.d(clz, "OnPause")
    }

    override fun onResume() {
        super.onResume()
        Log.d(clz, "OnResume")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(clz, "OnDestroy")
    }

}