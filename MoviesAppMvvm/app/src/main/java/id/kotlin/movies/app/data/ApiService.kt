package id.kotlin.movies.app.data

import id.kotlin.movies.app.BuildConfig
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/*
 * Copyright (C) 2020 the original author laminalfalah All Right Reserved.
 * 
 * This is part of the MoviesApp.
 * 
 * id.kotlin.movies.app.data
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

interface ApiService {

    @GET("/3/discover/movie")
    fun discoverMovie(
        @Query("api_key")
        apiKey: String = BuildConfig.API_KEY,

        @Query("page")
        page: Long
    ): Single<MainResponse>

    @GET("/3/discover/movie")
    fun discoverMovie(
        @Query("api_key")
        apiKey: String = BuildConfig.API_KEY
    ): Single<MainResponse>

    @GET("/3/movie/{movie_id}")
    fun detailMovie(
        @Path("movie_id")
        id: Int,
        @Query("api_key")
        apiKey: String = BuildConfig.API_KEY
    ): Single<Movie>

}