package ru.test.testingtasktable

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import ru.test.testingtasktable.di.MainDependencies

class TaskTableApp : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            modules(
                    MainDependencies.mainModule,
                )
            androidContext(this@TaskTableApp)
        }
    }
}