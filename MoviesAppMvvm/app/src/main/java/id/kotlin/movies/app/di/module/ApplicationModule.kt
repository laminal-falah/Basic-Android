package id.kotlin.movies.app.di.module

import dagger.Module
import dagger.Provides
import id.kotlin.movies.app.domain.executor.JobExecutor
import id.kotlin.movies.app.domain.executor.UIThread
import javax.inject.Singleton

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
class ApplicationModule {

    @Provides
    @Singleton
    fun providesJobExecutor(): JobExecutor = JobExecutor()

    @Provides
    @Singleton
    fun providesUiThread(): UIThread = UIThread()

}