package zj.app.taipeizootour.hilt.module

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import zj.app.taipeizootour.api.ITaipeiOpenDataApi
import zj.app.taipeizootour.api.TaipeiOpenDataApi

@Module
@InstallIn(value = [ApplicationComponent::class])
object ApiModule {

    @Provides
    fun provideTaipeiOpenDataApi(taipeiOpenDataApi: TaipeiOpenDataApi): ITaipeiOpenDataApi {
        return TaipeiOpenDataApi()
    }
}