package zj.app.taipeizootour.repo

import androidx.lifecycle.LiveData
import zj.app.taipeizootour.api.ITaipeiOpenDataApi
import zj.app.taipeizootour.api.data.DataSetMetadata
import zj.app.taipeizootour.api.data.success
import zj.app.taipeizootour.db.dao.AreaPlantsDao
import zj.app.taipeizootour.db.dao.ZooAreaDao
import zj.app.taipeizootour.db.dao.ZooPlantDao
import zj.app.taipeizootour.db.data.AreaWithPlants
import zj.app.taipeizootour.db.model.AreaPlantsCrossRef
import zj.app.taipeizootour.db.model.ZooArea
import zj.app.taipeizootour.db.model.ZooPlant

class ZooRepo(
    private val api: ITaipeiOpenDataApi,
    private val zooAreaDao: ZooAreaDao,
    private val plantsDao: ZooPlantDao,
    private val areaPlantsDao: AreaPlantsDao
) : IZooRepo {

    override fun getLiveArea(): LiveData<List<ZooArea>> {
        return zooAreaDao.queryAll()
    }

    override suspend fun getAreaPlants(areaId: Int): AreaWithPlants? {
        return areaPlantsDao.queryByArea(areaId)
    }

    override suspend fun fetchMeta(query: String): DataSetMetadata? {
        return api.getMeta(query)?.success?.result?.results?.firstOrNull()
    }

    override suspend fun fetchAreaIntro(id: String, rid: String) {
        api.getAreaIntroCsv(id, rid)?.success?.let { list ->
            val areas = list.mapNotNull { area ->
                area.no?.toIntOrNull()?.let { id ->
                    ZooArea(
                        areaId = id,
                        category = area.category ?: "",
                        name = area.name ?: "",
                        picUrl = area.picUrl ?: "",
                        info = area.info ?: "",
                        memo = area.memo ?: "",
                        latLng = area.latLng,
                        introUrl = area.introUrl
                    )
                }
            }
            zooAreaDao.insert(areas)
        }
    }

    override suspend fun fetchPlants(id: String) {
        val plants = mutableListOf<ZooPlant>()
        val areaPlantsCrossRef = mutableListOf<AreaPlantsCrossRef>()
        api.getPlantsJson(id)?.success?.result?.results?.forEach { plant ->
            val zooPlantEntity = ZooPlant(
                plantId = plant.id,
                latinName = plant.latinName,
                chName = plant.chNameWithBom ?: plant.chName ?: "",
                enName = plant.enName,
                alsoKnown = plant.alsoKnown,
                location = plant.location,
                geo = plant.geo,
                brief = plant.brief,
                summary = plant.summary,
                feature = plant.feature,
                family = plant.family,
                genus = plant.genus,
                function = plant.function,
                updateDate = plant.updateDate,
                pic01Url = plant.pic01Url,
                pic01Alt = plant.pic01Alt,
                pic02Url = plant.pic02Url,
                pic02Alt = plant.pic02Alt,
                pic03Url = plant.pic03Url,
                pic03Alt = plant.pic03Alt,
                pic04Url = plant.pic04Url,
                pic04Alt = plant.pic04Alt
            )
            plants.add(zooPlantEntity)
            val refs = plant.location.split("；").mapNotNull { areaName ->
                zooAreaDao.queryByName(areaName)?.areaId?.let {
                    AreaPlantsCrossRef(it, plant.id)
                }
            }
            areaPlantsCrossRef.addAll(refs)
        }
        plantsDao.insert(plants)
        areaPlantsDao.insert(areaPlantsCrossRef)
    }
}