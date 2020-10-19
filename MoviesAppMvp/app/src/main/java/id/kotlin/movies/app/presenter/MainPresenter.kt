package id.kotlin.movies.app.presenter

import id.kotlin.movies.app.data.ApiService
import id.kotlin.movies.app.data.MainResponse
import id.kotlin.movies.app.data.Movie
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/*
 * Copyright (C) 2020 the original author laminalfalah All Right Reserved.
 * 
 * This is part of the MoviesAppMvp.
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

class MainPresenter(private val view: MainBase, private val source: ApiService) {

    private val disposables: CompositeDisposable = CompositeDisposable()

    fun discoverMovie() {
        view.onShowLoading()

        source.discoverMovie()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ response ->
                view.onHideLoading()
                view.onResponse(response?.results ?: emptyList())
            }, { error ->
                view.onHideLoading()
                view.onFailure(error)
            }).addTo(disposables)
    }

    fun findMovie(id: Int) {
        view.onShowLoading()

        source.detailMovie(id)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ response ->
                view.onHideLoading()
                response?.let { view.onResponse(it) }
            }, { error ->
                view.onHideLoading()
                view.onFailure(error)
            }).addTo(disposables)
    }

    fun onDetach() {
        disposables.clear()
    }

}