package hu.bme.aut.shoppinglistapp.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import hu.bme.aut.shoppinglistapp.data.ShoppingListAppDatabase
import hu.bme.aut.shoppinglistapp.data.SLDAO
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {
    @Provides
    fun provideTodoDao(appDatabase: ShoppingListAppDatabase): SLDAO {
        return appDatabase.SLDao()
    }

    @Provides
    @Singleton
    fun provideTodoAppDatabase(
        @ApplicationContext appContext: Context): ShoppingListAppDatabase {
        return ShoppingListAppDatabase.getDatabase(appContext)
    }
}