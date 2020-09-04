package zj.app.taipeizootour.data.transformer

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import zj.app.taipeizootour.data.DerivedZooPlant
import zj.app.taipeizootour.db.model.ZooPlant
import javax.inject.Inject

class ZooPlantTransformer @Inject constructor(
    private val geoReader: IGeoReader,
    private val geoSpanGenerator: IGeoSpanGenerator
): IDataTransformer<ZooPlant, DerivedZooPlant> {

    private val locationDelimiter = "；"

    override suspend fun transform(data: ZooPlant?): DerivedZooPlant? {
        return withContext(Dispatchers.Default) {
            data?.let { zooPlant ->
                DerivedZooPlant(zooPlant).apply {
                    val coordinates = geoReader.read(geo)
                    geoMapLocation = geoSpanGenerator.generate(location, locationDelimiter, coordinates)
                }
            }
        }
    }

}