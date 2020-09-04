package zj.app.taipeizootour.hilt.module

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import zj.app.taipeizootour.data.transformer.DefaultGeoReader
import zj.app.taipeizootour.data.transformer.IGeoReader

@Module
@InstallIn(ActivityComponent::class)
abstract class GeoReaderModule {

    @Binds
    abstract fun bindDefaultGeoReader(geoReader: DefaultGeoReader): IGeoReader
}