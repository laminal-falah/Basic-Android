package com.example.lifecycle.arch

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.lifecycle.arch.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), LifecycleOwner {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        lifecycle.addObserver(MainLifeObserver())

        val model = ViewModelProvider(this)[MainViewModel::class.java]

        model.title.observe(this, { binding.tvHelloWorld.text = it })

        model.name.postValue("Live Data Arch")

        val nameObserver = Observer<String> { name -> binding.tvHelloWorld.text = name}
        model.name.observe(this, nameObserver)

    }
}