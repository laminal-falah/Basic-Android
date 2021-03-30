package id.kotlin.movies.app.presentation.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import id.kotlin.movies.app.data.ApiService
import io.reactivex.disposables.CompositeDisposable

/*
 * Copyright (C) 2021 the original author laminalfalah All Right Reserved.
 * 
 * id.kotlin.movies.app.presenter
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
 * Created by laminalfalah on 26/03/21
 */

class MainViewModel(
    private val dataSource: ApiService
): ViewModel(), MainView {

    private val disposables = CompositeDisposable()

    private val observer = MutableLiveData<MainViewState>()

    override val states: LiveData<MainViewState> get() = observer

    override fun discoverMovie() {
        dataSource.discoverMovie()
            .map<MainViewState>(MainViewState::Success)
            .onErrorReturn(MainViewState::Error)
            .toFlowable()
            .startWith(MainViewState.Loading)
            .subscribe(observer::postValue)
            .let(disposables::add)
    }

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }

}