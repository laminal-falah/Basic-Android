package id.kotlin.movies.app.presenter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textview.MaterialTextView
import id.kotlin.movies.app.R
import id.kotlin.movies.app.data.Result

/*
 * Copyright (C) 2020 the original author laminalfalah All Right Reserved.
 * 
 * This is part of the MoviesApp.
 * 
 * id.kotlin.movies.app.presenter
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
 * Created by laminalfalah on 18/10/20
 */

class MainAdapter(private val results: List<Result>): RecyclerView.Adapter<MainAdapter.DetailViewHolder>() {

    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailViewHolder {
        return DetailViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: DetailViewHolder, position: Int) {
        holder.bind(results[holder.adapterPosition])
        holder.itemView.setOnClickListener {
            onItemClickCallback.onItemClick(results[holder.adapterPosition])
        }
    }

    override fun getItemCount(): Int {
        return results.count()
    }

    inner class DetailViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        fun bind(result: Result) {
            with(itemView) {
                val title = findViewById<MaterialTextView>(R.id.tv_title)
                val overview = findViewById<MaterialTextView>(R.id.tv_overview)

                title.text = result.title
                overview.text = result.overview
            }
        }
    }

    interface OnItemClickCallback {
        fun onItemClick(data: Result)
    }
}