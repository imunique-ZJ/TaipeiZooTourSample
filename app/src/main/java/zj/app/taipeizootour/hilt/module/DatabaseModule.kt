package zj.app.taipeizootour.hilt.module

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import zj.app.taipeizootour.db.ZooDatabase
import zj.app.taipeizootour.db.dao.*
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideZooDatabase(@ApplicationContext context: Context): ZooDatabase {
        return ZooDatabase.getDatabase(context)
    }

    @Provides
    fun provideZooAreaDao(zooDatabase: ZooDatabase): ZooAreaDao = zooDatabase.zooAreaDao()

    @Provides
    fun provideAreaPlantsDao(zooDatabase: ZooDatabase): AreaPlantsDao = zooDatabase.areaPlantsDao()

    @Provides
    fun provideAreaAnimalsDao(zooDatabase: ZooDatabase): AreaAnimalsDao = zooDatabase.areaAnimalsDao()

    @Provides
    fun provideZooPlantDao(zooDatabase: ZooDatabase): ZooPlantDao = zooDatabase.zooPlantDao()

    @Provides
    fun provideZooAnimalDao(zooDatabase: ZooDatabase): ZooAnimalDao = zooDatabase.zooAnimalDao()

}