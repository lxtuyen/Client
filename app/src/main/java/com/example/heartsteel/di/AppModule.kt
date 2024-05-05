package com.example.heartsteel.di

import com.example.heartsteel.data.reponsitory.AuthRespository
import com.example.heartsteel.data.reponsitory.AuthRespositoryImpl
import com.google.firebase.auth.FirebaseAuth
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
    fun providesFirebaseAuth()  = FirebaseAuth.getInstance()

    @Provides
    @Singleton
    fun providesRepositoryImpl(firebaseAuth: FirebaseAuth):AuthRespository{
        return AuthRespositoryImpl(firebaseAuth)
    }
}