package id.kotlin.movies.app.presentation.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import id.kotlin.movies.app.R
import id.kotlin.movies.app.data.Result
import id.kotlin.movies.app.databinding.ItemBinding
import id.kotlin.movies.app.presentation.OnItemClickCallback
import id.kotlin.movies.app.presentation.main.MainAdapter.MainViewHolder

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

class MainAdapter(private val results: List<Result>): Adapter<MainViewHolder>(), OnItemClickCallback<Result> {

    private lateinit var onItemClickCallback: OnItemClickCallback<Result>

    override fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback<Result>) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder = MainViewHolder(
        DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item,
            parent,
            false
        )
    )

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.binding.apply {
            viewModel = MainAdapterViewModel(results[holder.bindingAdapterPosition])
            executePendingBindings()
        }

        holder.itemView.setOnClickListener {
            onItemClickCallback.onItemClick(results[holder.bindingAdapterPosition])
        }
    }

    override fun getItemCount(): Int = results.count()

    inner class MainViewHolder(val binding: ItemBinding) : ViewHolder(binding.root)

}