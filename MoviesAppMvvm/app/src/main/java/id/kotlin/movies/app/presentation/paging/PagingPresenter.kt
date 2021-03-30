package id.kotlin.movies.app.presentation.paging

import id.kotlin.movies.app.domain.MainEntity
import id.kotlin.movies.app.domain.MainParam
import id.kotlin.movies.app.domain.MainUseCase
import id.kotlin.movies.app.domain.common.DefaultObserver

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

class PagingPresenter(
    private val view: PagingView,
    private val useCase: MainUseCase
) {

    fun discoverMovie() {
        view.onShowLoading()
        useCase.execute(
            DiscoverMovieUseCase(),
            MainParam()
        )
    }

    fun loadMore(page: Long) {
        useCase.execute(
            LoadMoreUseCase(),
            MainParam(page)
        )
    }

    fun onDetach() {
        useCase.dispose()
    }

    inner class DiscoverMovieUseCase : DefaultObserver<MainEntity>() {
        override fun onSuccess(t: MainEntity) {
            view.onHideLoading()
            view.onSuccess(t)
        }

        override fun onError(e: Throwable) {
            view.onHideLoading()
            view.onError(e)
        }
    }

    inner class LoadMoreUseCase : DefaultObserver<MainEntity>() {
        override fun onSuccess(t: MainEntity) {
            view.onPaginationSuccess(t)
        }

        override fun onError(e: Throwable) {
            view.onPaginationError(e)
        }
    }

}