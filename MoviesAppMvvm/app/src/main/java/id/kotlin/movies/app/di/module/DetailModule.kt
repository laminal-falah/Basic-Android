package id.kotlin.movies.app.di.module

import dagger.Binds
import dagger.Module
import dagger.Provides
import id.kotlin.movies.app.data.ApiService
import id.kotlin.movies.app.presentation.detail.DetailActivity
import id.kotlin.movies.app.presentation.detail.DetailViewModel
import id.kotlin.movies.app.presentation.detail.DetailViewModelCallback
import retrofit2.Retrofit

/*
 * Copyright (C) 2020 the original author laminalfalah All Right Reserved.
 * 
 * This is part of the MoviesAppMvp.
 * 
 * id.kotlin.movies.app.di.module
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

@Module
abstract class DetailModule {

    companion object {
        @Provides
        fun providesMainDataSource(retrofit: Retrofit): ApiService = retrofit.create(ApiService::class.java)

        @Provides
        fun providesDetailViewModel(callback: DetailViewModelCallback, source: ApiService): DetailViewModel = DetailViewModel(callback, source)
    }

    @Binds
    abstract fun bindDetailViewModelCallback(activity: DetailActivity): DetailViewModelCallback

}