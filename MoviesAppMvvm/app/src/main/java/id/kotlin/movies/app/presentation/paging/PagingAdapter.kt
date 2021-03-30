package id.kotlin.movies.app.presentation.paging

import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import id.kotlin.movies.app.R
import id.kotlin.movies.app.databinding.ItemLoadingBinding
import id.kotlin.movies.app.databinding.ItemPagingBinding
import id.kotlin.movies.app.domain.MainResult
import id.kotlin.movies.app.presentation.OnItemClickCallback

/*
 * Copyright (C) 2021 the original author laminalfalah All Right Reserved.
 * 
 * id.kotlin.movies.app.presentation
 * 
 * This is part of the MoviesAppMvvm.
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
 * Created by laminalfalah on 30/03/21
 */

class PagingAdapter(private val results: MutableList<MainResult?>): Adapter<ViewHolder>(), OnItemClickCallback<MainResult> {

    enum class Type {
        DATA,
        LOADING
    }

    private lateinit var onItemClickCallback: OnItemClickCallback<MainResult>

    override fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback<MainResult>) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        when (viewType) {
            Type.DATA.ordinal -> {
                PagingViewHolder(
                    DataBindingUtil.inflate(
                        LayoutInflater.from(parent.context),
                        R.layout.item_paging,
                        parent,
                        false
                    )
                )
            }
            Type.LOADING.ordinal -> {
                LoadingViewHolder(
                    DataBindingUtil.inflate(
                        LayoutInflater.from(parent.context),
                        R.layout.item_loading,
                        parent,
                        false
                    )
                )
            }
            else -> throw IllegalStateException("Illegal view type")
        }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        when (holder) {
            is PagingViewHolder -> {
                holder.binding.apply {
                    viewModel = results[holder.bindingAdapterPosition]?.let { PagingAdapterViewModel(it) }
                    executePendingBindings()
                }
                holder.itemView.setOnClickListener {
                    results[holder.bindingAdapterPosition]?.let { onItemClickCallback.onItemClick(it) }
                }
            }
        }
    }

    override fun getItemCount(): Int = results.count()

    override fun getItemViewType(position: Int): Int = when {
        results[position] == null -> Type.LOADING.ordinal
        else -> Type.DATA.ordinal
    }

    fun onShowLoading() {
        results.add(null)
        Handler(Looper.getMainLooper()).post { notifyItemInserted(results.count().minus(1)) }
    }

    fun onHideLoading() {
        results.removeAt(results.count().minus(1))
        Handler(Looper.getMainLooper()).post { notifyItemRemoved(results.count()) }
    }

    fun loadMore(results: MutableList<MainResult>) {
        this.results.addAll(results)
        Handler(Looper.getMainLooper()).post { notifyDataSetChanged() }
    }

    inner class PagingViewHolder(val binding: ItemPagingBinding) : ViewHolder(binding.root)

    inner class LoadingViewHolder(val binding: ItemLoadingBinding) : ViewHolder(binding.root)

}