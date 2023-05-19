package com.kaibltdinc.melancholian

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module


val appModule = module {
    single { Saving(androidContext()) }
    single<SharedPreferences> { androidContext().getSharedPreferences("naming", AppCompatActivity.MODE_PRIVATE) }
    viewModel { MyViewModel(androidContext(), get()) }

}

fun setupKoin(context: Context) {
    startKoin {
        androidContext(context)
        modules(appModule)
    }
}