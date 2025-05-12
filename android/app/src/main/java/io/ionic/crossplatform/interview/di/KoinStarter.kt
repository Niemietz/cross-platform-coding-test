package io.ionic.crossplatform.interview.di

import android.content.Context
import io.ionic.crossplatform.plugins.todo.di.pluginModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

object KoinStarter {
    fun start(context: Context) {
        startKoin {
            androidContext(context)
            androidLogger(Level.NONE)
            modules(
                pluginModule,
            )
        }
    }
}