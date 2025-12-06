package com.drodobyte.core.data.local.di

import com.drodobyte.core.data.local.Factory

//@Module
//@InstallIn(SingletonComponent::class)
internal class LocalDataModule {

//    @Provides
//    @Singleton
    fun localDataSource(dao: Any) =
        Factory.dataSource(dao)

//    @Provides
//    @Singleton
    fun foodDao(database: Database) =
        Factory.dao(database)

//    @Provides
//    @Singleton
//    fun database(@ApplicationContext context: Context) =
//        Factory.database(context)
}
