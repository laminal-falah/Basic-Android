package id.kotlin.movies.app.presenter

import id.kotlin.movies.app.data.Movie
import id.kotlin.movies.app.data.Result

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

interface MainBase {

    fun onShowLoading()
    fun onHideLoading()
    fun onResponse(results: List<Result>)
    fun onResponse(result: Movie)
    fun onFailure(error: Throwable)

}