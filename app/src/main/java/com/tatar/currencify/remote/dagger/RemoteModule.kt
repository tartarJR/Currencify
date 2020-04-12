package com.tatar.currencify.remote.dagger

import com.tatar.currencify.data.RemoteDataSource
import com.tatar.currencify.remote.source.RemoteDataSourceImpl
import dagger.Binds
import dagger.Module

@Module
abstract class RemoteModule {
    @Binds
    abstract fun bindRemoteDataSource(remoteDataSource: RemoteDataSourceImpl): RemoteDataSource
}
