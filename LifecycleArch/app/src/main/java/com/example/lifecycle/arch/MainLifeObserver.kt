package com.example.lifecycle.arch

import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent


/*
 * Copyright (C) 2021 the original author laminalfalah All Right Reserved.
 * 
 * com.example.lifecycle.arch
 * 
 * This is part of the Lifecycle Arch.
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

class MainLifeObserver: LifecycleObserver {

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun connect() = Log.d(this.javaClass.simpleName, "Connect")

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun disconnect() = Log.d(this.javaClass.simpleName, "Disconnect")
}