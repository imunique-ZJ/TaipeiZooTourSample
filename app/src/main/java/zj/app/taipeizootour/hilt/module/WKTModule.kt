package zj.app.taipeizootour.hilt.module

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import org.locationtech.jts.io.WKTReader
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object WKTModule {

    @Singleton
    @Provides
    fun provideWKTReader(): WKTReader {
        return WKTReader()
    }
}