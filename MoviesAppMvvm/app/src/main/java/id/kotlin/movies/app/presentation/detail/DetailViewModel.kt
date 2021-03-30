package id.kotlin.movies.app.presentation.detail

import android.view.View
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.library.baseAdapters.BR
import id.kotlin.movies.app.data.ApiService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo

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
 * Created by laminalfalah on 28/03/21
 */

class DetailViewModel(
    private val callback: DetailViewModelCallback,
    private val dataSource: ApiService
): BaseObservable(), DetailView {

    var progressBarVisibility: Int = View.GONE
    @Bindable get

    private val disposables: CompositeDisposable = CompositeDisposable()

    override fun findByMovie(id: Int) {
        progressBarVisibility = View.VISIBLE
        notifyPropertyChanged(BR.progressBarVisibility)

        dataSource.detailMovie(id)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ response ->
                progressBarVisibility = View.GONE
                notifyPropertyChanged(BR.progressBarVisibility)
                callback.onSuccess(response)
            }, { error ->
                progressBarVisibility = View.GONE
                notifyPropertyChanged(BR.progressBarVisibility)
                callback.onError(error)
            }).addTo(disposables)
    }

    override fun onDetach() {
        disposables.clear()
    }

}