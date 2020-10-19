package id.kotlin.belajar

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textview.MaterialTextView

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

class ListAdapter: RecyclerView.Adapter<ListAdapter.DetailViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailViewHolder {
        return DetailViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: DetailViewHolder, position: Int) {
        holder.bind()
    }

    override fun getItemCount(): Int {
        return 10
    }

    inner class DetailViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        fun bind() {
            with(itemView) {
                val detailText = findViewById<MaterialTextView>(R.id.tv_item_title)
                val text = "RecyclerView ! Position -> $adapterPosition"
                detailText.text = text

                itemView.setOnClickListener {
                    Snackbar.make(itemView, text, Snackbar.LENGTH_LONG).show()
                }
            }
        }
    }
}