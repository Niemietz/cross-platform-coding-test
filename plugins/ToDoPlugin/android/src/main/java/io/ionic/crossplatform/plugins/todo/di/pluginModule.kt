package io.ionic.crossplatform.plugins.todo.di

import io.ionic.crossplatform.plugins.todo.Constants.Database.DATABASE_NAME
import io.ionic.crossplatform.plugins.todo.DataAccessObject
import io.ionic.crossplatform.plugins.todo.DataAccessObjectImpl
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val pluginModule = module {
     single<DataAccessObject> {
         DataAccessObjectImpl(
             androidContext(),
             DATABASE_NAME,
             1,
         )
     }
}