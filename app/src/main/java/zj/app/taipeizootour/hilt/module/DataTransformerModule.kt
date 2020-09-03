package zj.app.taipeizootour.hilt.module

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import zj.app.taipeizootour.data.DerivedZooAnimal
import zj.app.taipeizootour.data.DerivedZooPlant
import zj.app.taipeizootour.data.transformer.IDataTransformer
import zj.app.taipeizootour.data.transformer.ZooAnimalTransformer
import zj.app.taipeizootour.data.transformer.ZooPlantTransformer
import zj.app.taipeizootour.db.model.ZooAnimal
import zj.app.taipeizootour.db.model.ZooPlant
import zj.app.taipeizootour.hilt.qualifier.ZooAnimalTransformerQualifier
import zj.app.taipeizootour.hilt.qualifier.ZooPlantTransformerQualifier

@Module
@InstallIn(ActivityComponent::class)
abstract class DataTransformerModule {

    @Binds
    @ZooPlantTransformerQualifier
    abstract fun bindZooPlantTransformer(
        zooPlantTransformer: ZooPlantTransformer
    ): IDataTransformer<ZooPlant, DerivedZooPlant>

    @Binds
    @ZooAnimalTransformerQualifier
    abstract fun binZooAnimalTransformer(
        zooAnimalTransformer: ZooAnimalTransformer
    ): IDataTransformer<ZooAnimal, DerivedZooAnimal>
}