package com.otb.githubtracker.network

import com.otb.githubtracker.network.interceptor.NetworkStateChecker
import com.otb.githubtracker.network.interceptor.NetworkStateCheckerImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/**
 * Created by Mohit Rajput on 13/08/22.
 */

@Module
@InstallIn(SingletonComponent::class)
interface NetworkBindingModule {
    @Binds
    fun bindNetworkStateChecker(networkStateCheckerImpl: NetworkStateCheckerImpl): NetworkStateChecker
}