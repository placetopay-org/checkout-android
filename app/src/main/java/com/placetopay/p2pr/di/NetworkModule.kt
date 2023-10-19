package com.placetopay.p2pr.di

import android.content.Context
import com.placetopay.p2pr.BuildConfig
import com.placetopay.p2pr.api.CheckoutService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class NetworkModule {

    @Singleton
    @Provides
    fun provideCheckoutService(@ApplicationContext context: Context): CheckoutService {
        return CheckoutService.create(context, BuildConfig.URL_CHECKOUT)
    }
}