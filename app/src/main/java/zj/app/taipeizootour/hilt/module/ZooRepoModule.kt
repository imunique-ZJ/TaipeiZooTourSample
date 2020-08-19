package zj.app.taipeizootour.hilt.module

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.components.FragmentComponent
import zj.app.taipeizootour.repo.IZooRepo
import zj.app.taipeizootour.repo.ZooRepo

@Module
@InstallIn(value = [ActivityComponent::class, FragmentComponent::class])
abstract class ZooRepoModule {

    @Binds
    abstract fun bindZooRepo(zooRepo: ZooRepo): IZooRepo
}