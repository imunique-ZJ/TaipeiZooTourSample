package zj.app.taipeizootour.hilt.module

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import zj.app.taipeizootour.db.ZooDatabase
import zj.app.taipeizootour.db.dao.AreaPlantsDao
import zj.app.taipeizootour.db.dao.ZooAreaDao
import zj.app.taipeizootour.db.dao.ZooPlantDao

@Module
@InstallIn(ApplicationComponent::class)
object DatabaseModule {

    @Provides
    fun provideZooDatabase(@ApplicationContext context: Context): ZooDatabase {
        return ZooDatabase.getDatabase(context)
    }

    @Provides
    fun provideZooAreaDao(zooDatabase: ZooDatabase): ZooAreaDao = zooDatabase.zooAreaDao()

    @Provides
    fun provideAreaPlantsDao(zooDatabase: ZooDatabase): AreaPlantsDao = zooDatabase.areaPlantsDao()

    @Provides
    fun provideZooPlantDao(zooDatabase: ZooDatabase): ZooPlantDao = zooDatabase.zooPlantDao()
}