package zj.app.taipeizootour.hilt.module

import android.content.Context
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.components.FragmentComponent
import dagger.hilt.android.qualifiers.ActivityContext
import zj.app.taipeizootour.const.Constants

@Module
@InstallIn(value = [ActivityComponent::class, FragmentComponent::class])
object SharedPreferenceModule {

    @Provides
    fun provideZooSharedPreference(@ActivityContext context: Context): SharedPreferences {
        return context.getSharedPreferences(Constants.KEY_SHARED_PREF_NAME, Context.MODE_PRIVATE)
    }
}