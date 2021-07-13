package com.testbozz.testbozz.di

import android.os.Build
import com.testbozz.testbozz.data.api.BozzApi
import com.testbozz.testbozz.utils.API_KEY
import com.testbozz.testbozz.utils.API_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import javax.inject.Singleton
import kotlin.jvm.Throws

@Module
@InstallIn(SingletonComponent::class )
object RetrofitModule {

    @Singleton //Para generar solo una instancia del objecto
    @Provides //Para poder proveer este objecto en cualquier clase
    fun provideGsonConverterFactory(): GsonConverterFactory {
        return GsonConverterFactory.create()
    }


    @Singleton //Para generar solo una instancia del objecto
    @Provides //Para poder proveer este objecto en cualquier clase
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC)
    }

    @Singleton //Para generar solo una instancia del objecto
    @Provides //Para poder proveer este objecto en cualquier clase
    fun clientInterceptor(): Interceptor {
        return object : Interceptor {
            @Throws(IOException::class)
            override fun intercept(chain: Interceptor.Chain): Response {
                var request = chain.request()
                val url =
                    request.url.newBuilder()
                        .build()
                request = request.newBuilder()
                    .addHeader("Content-Type", "application/json")
                    .addHeader("X-Requested-With", "XMLHttpRequest")
                    .addHeader("x-device-type", Build.DEVICE)
                    .url(url)
                    .build()
                return chain.proceed(request)
            }
        }
    }

    @Singleton //Para generar solo una instancia del objecto
    @Provides //Para poder proveer este objecto en cualquier clase
    fun provideOkHttpClient(
        httpLoggingInterceptor: HttpLoggingInterceptor,
        clientInterceptor: Interceptor
    ): OkHttpClient {
        return OkHttpClient.Builder().addNetworkInterceptor(clientInterceptor)
            .addInterceptor(httpLoggingInterceptor).build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(
        gson: GsonConverterFactory,
        http: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(API_URL) //Configurar el base URL
            .addConverterFactory(gson) //Se agrega el GSON que se va a convertir uno generico
            .client(http) //Se agrega el cliente para que se pruebe el interceptor
            .build()
    }


    @Provides
    @Singleton
    fun provideApiJson(retrofit : Retrofit) : BozzApi {
        return retrofit.create(BozzApi::class.java)
    }
}