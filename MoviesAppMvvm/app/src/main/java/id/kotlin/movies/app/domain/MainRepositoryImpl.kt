package id.kotlin.movies.app.domain

import id.kotlin.movies.app.data.MainFactory
import io.reactivex.Single

/*
 * Copyright (C) 2021 the original author laminalfalah All Right Reserved.
 * 
 * id.kotlin.movies.app.domain
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

class MainRepositoryImpl(private val factory: MainFactory): MainRepository {

    override fun discoverMovie(param: MainParam): Single<MainEntity> =
        factory.discoveryMovie(param.page).map { response ->
            MainEntity(
                page = response.page ?: -1L,
                totalPages = response.totalPages ?: -1L,
                results = response.results?.map { result ->
                    MainResult(
                        id = result.id,
                        title = result.title,
                        overview = result.overview
                    )
                }?.toMutableList() ?: mutableListOf()
            )
        }
}