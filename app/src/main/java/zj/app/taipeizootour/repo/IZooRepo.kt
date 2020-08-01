package zj.app.taipeizootour.repo

import androidx.lifecycle.LiveData
import zj.app.taipeizootour.api.data.DataSetMetadata
import zj.app.taipeizootour.db.data.AreaWithPlants
import zj.app.taipeizootour.db.model.ZooArea

interface IZooRepo {

    fun getLiveArea(): LiveData<List<ZooArea>>
    suspend fun getAreaPlants(areaId: Int): AreaWithPlants?
    suspend fun fetchMeta(query: String): DataSetMetadata?
    suspend fun fetchAreaIntro(id: String, rid: String)
    suspend fun fetchPlants(id: String)

}