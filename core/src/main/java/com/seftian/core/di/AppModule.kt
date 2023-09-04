package com.seftian.core.di

import android.content.Context
import androidx.room.Room
import com.seftian.core.BuildConfig
import com.seftian.core.data.local.CapstoneAppDb
import com.seftian.core.data.local.LocalDataSource
import com.seftian.core.data.remote.MovieRemoteDataSource
import com.seftian.core.data.remote.network.MovieApi
import com.seftian.core.domain.usecase.MoviesInteractor
import com.seftian.core.domain.usecase.MoviesUseCase
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
    fun provideMovieApi(): MovieApi {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor.Level.BODY
            } else {
                HttpLoggingInterceptor.Level.NONE
            }
        }

        val apiKeyInterceptor = Interceptor { chain ->
            val originalRequest = chain.request()

            val modifiedRequest = originalRequest.newBuilder()
                .header("Authorization", "Bearer ${BuildConfig.API_KEY}")
                .build()

            chain.proceed(modifiedRequest)
        }

        val client = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(apiKeyInterceptor)
            .build()

        return Retrofit.Builder()
            .baseUrl(MovieApi.BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MovieApi::class.java)
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
    fun provideLocalDataSource(capstoneAppDb: CapstoneAppDb): LocalDataSource{
        return LocalDataSource(capstoneAppDb.moviesDao)
    }

    @Provides
    @Singleton
    fun provideMovieRemoteDataSource(movieApi: MovieApi): MovieRemoteDataSource {
        return MovieRemoteDataSource(movieApi)
    }


    @Provides
    @Singleton
    fun provideMoviesRepository(
        movieRemoteDataSource: MovieRemoteDataSource,
        localDataSource: LocalDataSource
    ): com.seftian.core.data.MoviesRepository {
        return com.seftian.core.data.MoviesRepository(movieRemoteDataSource, localDataSource)
    }

    @Provides
    @Singleton
    fun provideMoviesUsecase(moviesRepository: com.seftian.core.data.MoviesRepository): MoviesUseCase {
        return MoviesInteractor(moviesRepository)
    }
}