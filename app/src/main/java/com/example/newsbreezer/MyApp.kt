package com.example.newsbreezer

import android.app.Application
import android.content.Context
import com.example.newsbreezer.data.network.ApiService
import com.example.newsbreezer.data.network.NetworkConnectionInterceptor
import com.example.newsbreezer.data.repository.MainRepository
import com.example.newsbreezer.view.HomeViewModelFactory

import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton


class MyApp : Application(), KodeinAware {

    companion object {
        lateinit var  appContext: Context
    }

    override fun onCreate() {
        super.onCreate()
        appContext = this
    }

    override val kodein = Kodein.lazy {
        import(androidXModule(this@MyApp))

        bind() from singleton { NetworkConnectionInterceptor(instance()) }
        bind() from singleton { ApiService(instance()) }

        bind() from singleton { MainRepository(instance()) }
        bind() from singleton { HomeViewModelFactory(instance()) }

    }


}

