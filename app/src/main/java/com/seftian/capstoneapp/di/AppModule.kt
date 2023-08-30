package com.seftian.capstoneapp.di

import android.content.Context
import androidx.room.Room
import com.seftian.capstoneapp.data.remote.network.Api
import com.seftian.capstoneapp.BuildConfig
import com.seftian.capstoneapp.data.GamesRepository
import com.seftian.capstoneapp.data.local.CapstoneAppDb
import com.seftian.capstoneapp.data.local.LocalDataSource
import com.seftian.capstoneapp.data.remote.RemoteDataSource
import com.seftian.capstoneapp.domain.usecase.GameInteractor
import com.seftian.capstoneapp.domain.usecase.GamesUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideApi(): Api {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor.Level.BODY
            } else {
                HttpLoggingInterceptor.Level.NONE
            }
        }

        val apiKeyInterceptor = Interceptor { chain ->
            val originalRequest = chain.request()
            val originalUrl = originalRequest.url
            val newUrl = originalUrl.newBuilder()
                .addQueryParameter("key", BuildConfig.API_KEY)
                .build()

            val modifiedRequest = originalRequest.newBuilder()
                .url(newUrl)
                .build()

            chain.proceed(modifiedRequest)
        }

        val client = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(apiKeyInterceptor)
            .build()

        return Retrofit.Builder()
            .baseUrl(Api.BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(Api::class.java)
    }

    @Provides
    @Singleton
    fun provideLocalDb(@ApplicationContext context: Context): CapstoneAppDb {
        return Room.databaseBuilder(
            context,
            CapstoneAppDb::class.java,
            "capstone.db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideLocalDataSource(capstoneAppDb: CapstoneAppDb): LocalDataSource {
        return LocalDataSource(capstoneAppDb.gamesDao)
    }

    @Provides
    @Singleton
    fun provideRemoteDataSource(api: Api): RemoteDataSource {
        return RemoteDataSource(api)
    }

    @Provides
    @Singleton
    fun provideGameRepository(
        localDataSource: LocalDataSource,
        remoteDataSource: RemoteDataSource
    ): GamesRepository {
        return GamesRepository(localDataSource, remoteDataSource)
    }

    @Provides
    @Singleton
    fun provideGamesUseCase(gamesRepository: GamesRepository): GamesUseCase {
        return GameInteractor(gamesRepository)
    }
}