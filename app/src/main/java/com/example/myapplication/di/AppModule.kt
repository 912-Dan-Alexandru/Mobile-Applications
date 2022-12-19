package com.example.myapplication.di

import android.app.Application
import androidx.room.Room
import com.example.myapplication.data.BookDatabase
import com.example.myapplication.data.BookRepository
import com.example.myapplication.data.BookRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideBookDatabase(app: Application): BookDatabase {
        return Room.databaseBuilder(
            app,
            BookDatabase::class.java,
            "book_db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideBookRepository(db: BookDatabase): BookRepository {
        return BookRepositoryImpl(db.dao)
    }
}