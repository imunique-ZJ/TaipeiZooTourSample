package zj.app.taipeizootour.hilt.module

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.components.FragmentComponent
import zj.app.taipeizootour.hilt.qualifier.YYYYmmddHHmmssDateFormat
import java.text.SimpleDateFormat
import java.util.*

@Module
@InstallIn(value = [ActivityComponent::class, FragmentComponent::class])
object SimpleDateFormatModule {

    @YYYYmmddHHmmssDateFormat
    @Provides
    fun provideYYYYmmddHHmmssDateFormat(): SimpleDateFormat {
        return SimpleDateFormat("yyyy-mm-dd HH:mm:ss", Locale.getDefault())
    }
}