package zj.app.taipeizootour.repo

import androidx.lifecycle.LiveData
import zj.app.taipeizootour.api.ITaipeiOpenDataApi
import zj.app.taipeizootour.api.data.DataSetMetadata
import zj.app.taipeizootour.api.data.success
import zj.app.taipeizootour.db.dao.*
import zj.app.taipeizootour.db.data.AreaWithAnimals
import zj.app.taipeizootour.db.data.AreaWithPlants
import zj.app.taipeizootour.db.model.*
import javax.inject.Inject

class ZooRepo @Inject constructor(
    private val api: ITaipeiOpenDataApi,
    private val zooAreaDao: ZooAreaDao,
    private val plantsDao: ZooPlantDao,
    private val animalDao: ZooAnimalDao,
    private val areaPlantsDao: AreaPlantsDao,
    private val areaAnimalsDao: AreaAnimalsDao
) : IZooRepo {

    override fun getLiveArea(): LiveData<List<ZooArea>> {
        return zooAreaDao.queryAll()
    }

    override suspend fun getAreaPlants(areaId: Int): AreaWithPlants? {
        return areaPlantsDao.queryByArea(areaId)
    }

    override suspend fun getAreaAnimals(areaId: Int): AreaWithAnimals? {
        return areaAnimalsDao.queryByArea(areaId)
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

    override suspend fun fetchAnimals(id: String) {
        val animals = mutableListOf<ZooAnimal>()
        val areaAnimalsCrossRef = mutableListOf<AreaAnimalsCrossRef>()
        api.getAnimalsJson(id)?.success?.result?.results?.forEach { animal ->
            val zooAnimalEntity = ZooAnimal(
                animalId = animal.id,
                code = animal.code,
                location = animal.location,
                latinName = animal.latinName,
                chName = animal.chName,
                enName = animal.enName,
                alsoKnown = animal.alsoKnown,
                geo = animal.geo,
                phylum = animal.phylum,
                clazz = animal.clazz,
                order = animal.order,
                family = animal.family,
                behavior = animal.behavior,
                distribution = animal.distribution,
                feature = animal.feature,
                diet = animal.diet,
                habitat = animal.habitat,
                conservation = animal.conservation,
                crisis = animal.crisis,
                interpretation = animal.interpretation,
                summary = animal.summary,
                updateDate = animal.updateDate,
                pic01Url = animal.pic01Url,
                pic01Alt = animal.pic01Alt,
                pic02Url = animal.pic02Url,
                pic02Alt = animal.pic02Alt,
                pic03Url = animal.pic03Url,
                pic03Alt = animal.pic03Alt,
                pic04Url = animal.pic04Url,
                pic04Alt = animal.pic04Alt,
                voice01Url = animal.voice01Url,
                voice01Alt = animal.voice01Alt,
                voice02Url = animal.voice02Url,
                voice02Alt = animal.voice02Alt,
                voice03Url = animal.voice03Url,
                voice03Alt = animal.voice03Alt,
                videoUrl = animal.videoUrl,
                pdf01Url = animal.pdf01Url,
                pdf01Alt = animal.pdf01Alt,
                pdf02Url = animal.pdf02Url,
                pdf02Alt = animal.pdf02Alt
            )
            animals.add(zooAnimalEntity)
            val refs = animal.location.split("；").mapNotNull { areaName ->
                zooAreaDao.queryByName(areaName)?.areaId?.let {
                    AreaAnimalsCrossRef(it, animal.id)
                }
            }
            areaAnimalsCrossRef.addAll(refs)
        }
        animalDao.insert(animals)
        areaAnimalsDao.insert(areaAnimalsCrossRef)
    }
}