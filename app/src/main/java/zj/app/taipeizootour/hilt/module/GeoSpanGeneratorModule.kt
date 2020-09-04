package zj.app.taipeizootour.hilt.module

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import zj.app.taipeizootour.data.transformer.DefaultGeoSpanGenerator
import zj.app.taipeizootour.data.transformer.IGeoSpanGenerator

@Module
@InstallIn(ActivityComponent::class)
abstract class GeoSpanGeneratorModule {

    @Binds
    abstract fun bindDefaultGeoSpanGenerator(geoSpanGenerator: DefaultGeoSpanGenerator): IGeoSpanGenerator
}