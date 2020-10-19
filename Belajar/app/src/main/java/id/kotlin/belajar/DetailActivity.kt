package id.kotlin.belajar

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.button.MaterialButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

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
 * Created by laminalfalah on 17/10/20
 */

class DetailActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val root = findViewById<ConstraintLayout>(R.id.root_detail)

        val tvText = findViewById<TextInputLayout>(R.id.tl_input)

        val etText = findViewById<TextInputEditText>(R.id.et_input)

        val btnSubmit = findViewById<MaterialButton>(R.id.btn_submit_detail)

        setupUI(root)

        btnSubmit.setOnClickListener {
            hideKeyboard(this)

            if (etText.length() < 1) {
                tvText.isErrorEnabled = true
                tvText.error = "Text Field Is Empty !"
            } else {
                tvText.isErrorEnabled = false
                tvText.error = null
                Snackbar.make(root, etText.text.toString(), Snackbar.LENGTH_LONG).show()
            }
        }

    }

    private fun hideKeyboard(activity: Activity) {
        val inputMethodManager: InputMethodManager = activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(activity.window.decorView.rootView.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setupUI(view: View) {
        view.setOnTouchListener { _, _ -> false }
    }

}