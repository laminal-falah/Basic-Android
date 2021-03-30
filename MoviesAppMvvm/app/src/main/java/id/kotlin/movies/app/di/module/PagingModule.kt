package id.kotlin.movies.app.di.module

import dagger.Binds
import dagger.Module
import dagger.Provides
import id.kotlin.movies.app.data.ApiService
import id.kotlin.movies.app.data.MainFactory
import id.kotlin.movies.app.di.scope.Presentation
import id.kotlin.movies.app.domain.MainRepository
import id.kotlin.movies.app.domain.MainRepositoryImpl
import id.kotlin.movies.app.domain.MainUseCase
import id.kotlin.movies.app.domain.executor.JobExecutor
import id.kotlin.movies.app.domain.executor.UIThread
import id.kotlin.movies.app.presentation.paging.PagingActivity
import id.kotlin.movies.app.presentation.paging.PagingPresenter
import id.kotlin.movies.app.presentation.paging.PagingView
import retrofit2.Retrofit

/*
 * Copyright (C) 2021 the original author laminalfalah All Right Reserved.
 * 
 * id.kotlin.movies.app.di.module
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

@Module
abstract class PagingModule {

    companion object {
        @Provides
        @Presentation
        fun providesPagingDataSource(retrofit: Retrofit): ApiService = retrofit.create(ApiService::class.java)

        @Provides
        @Presentation
        fun providesFactory(dataSource: ApiService): MainFactory = MainFactory(dataSource)

        @Provides
        @Presentation
        fun providesRepository(factory: MainFactory): MainRepositoryImpl = MainRepositoryImpl(factory)

        @Provides
        @Presentation
        fun providesUseCase(
            repository: MainRepository,
            executor: JobExecutor,
            thread: UIThread
        ): MainUseCase = MainUseCase(repository, executor, thread)

        @Provides
        fun providesPagingPresenter(pageView: PagingView, useCase: MainUseCase) = PagingPresenter(pageView, useCase)
    }

    @Binds
    abstract fun bindRepository(repositoryImpl: MainRepositoryImpl): MainRepository

    @Binds
    abstract fun bindPagingBase(activity: PagingActivity): PagingView
}