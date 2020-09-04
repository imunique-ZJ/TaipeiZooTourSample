package zj.app.taipeizootour.data.transformer

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import zj.app.taipeizootour.data.DerivedZooAnimal
import zj.app.taipeizootour.db.model.ZooAnimal
import javax.inject.Inject

class ZooAnimalTransformer @Inject constructor(
    private val geoReader: IGeoReader,
    private val geoSpanGenerator: IGeoSpanGenerator
): IDataTransformer<ZooAnimal, DerivedZooAnimal> {

    private val locationDelimiter = "；"

    override suspend fun transform(data: ZooAnimal?): DerivedZooAnimal? {
        return withContext(Dispatchers.Default) {
            data?.let { animal ->
                DerivedZooAnimal(animal).apply {
                    val coordinates = geoReader.read(geo)
                    geoMapLocation = geoSpanGenerator.generate(location, locationDelimiter, coordinates)
                }
            }
        }
    }

}