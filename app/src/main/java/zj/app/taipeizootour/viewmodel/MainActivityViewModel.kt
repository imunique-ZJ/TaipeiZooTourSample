package zj.app.taipeizootour.viewmodel

import android.content.SharedPreferences
import android.util.Log
import androidx.core.content.edit
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import kotlinx.coroutines.*
import zj.app.taipeizootour.api.data.DataSetMetadata
import zj.app.taipeizootour.const.Constants
import zj.app.taipeizootour.db.data.AreaWithAnimals
import zj.app.taipeizootour.db.data.AreaWithPlants
import zj.app.taipeizootour.db.model.ZooArea
import zj.app.taipeizootour.db.model.ZooPlant
import zj.app.taipeizootour.hilt.qualifier.YYYYmmddHHmmssDateFormat
import zj.app.taipeizootour.repo.IZooRepo
import zj.app.taipeizootour.state.ZooAreaState
import java.text.SimpleDateFormat

class MainActivityViewModel @ViewModelInject constructor(
    private val sharedPreference: SharedPreferences,
    @YYYYmmddHHmmssDateFormat private val timeFormat: SimpleDateFormat,
    private val zooRepo: IZooRepo
) : ViewModel() {

    private val tagName = javaClass.simpleName
    private val areaWithPlants = MutableLiveData<AreaWithPlants?>()
    private val areaWithAnimals = MutableLiveData<AreaWithAnimals?>()
    private val selectedArea = MutableLiveData<ZooArea?>()
    private val selectedPlantId = MutableLiveData<ZooPlant?>()

    val state = MutableLiveData<ZooAreaState>(ZooAreaState.Loading)
    val areaLiveData = zooRepo.getLiveArea()
    val areaWithPlantsLiveData: LiveData<AreaWithPlants?> = areaWithPlants
    val selectedAreaLiveData: LiveData<ZooArea?> = selectedArea
    val selectedPlantIdLiveData: LiveData<ZooPlant?> = selectedPlantId

    fun fetchData(areaIntroQuery: String, plantsQuery: String, animalsQuery: String) {
        viewModelScope.launch {
            state.value = ZooAreaState.Loading
            val areaIntroMeta = getNewMeta(areaIntroQuery, Constants.KEY_SP_ZOO_AREA_INTRO_DATE_TIME)
            val areaIntroRid = areaIntroMeta?.resources?.firstOrNull()?.resourceId
            if (areaIntroRid != null) {
                zooRepo.fetchAreaIntro(areaIntroMeta.id, areaIntroRid)
                awaitAll(
                    async { fetchPlantsInfo(plantsQuery) },
                    async { fetchAnimalsInfo(animalsQuery) }
                )
            }
            state.value = ZooAreaState.Finish
        }
    }

    fun selectArea(area: ZooArea) {
        selectedArea.value = area
    }

    fun selectPlant(plant: ZooPlant) {
        selectedPlantId.value = plant
    }

    private suspend fun getNewMeta(query: String, dataTimePrefKey: String): DataSetMetadata? {
        return withContext(Dispatchers.IO) {
            zooRepo.fetchMeta(query)?.takeIf { meta ->
                val dataTime = timeFormat.parse(meta.metadataModified)?.time ?: -1L
                val originalDataTime = sharedPreference.getLong(dataTimePrefKey, -1L)
                val hasUpdate = dataTime > originalDataTime
                if (hasUpdate) {
                    sharedPreference.edit {
                        putLong(dataTimePrefKey, dataTime)
                    }
                }
                return@takeIf hasUpdate
            }
        }
    }

    private suspend fun fetchPlantsInfo(plantsQuery: String) {
        try {
            val plantsMeta = getNewMeta(plantsQuery, Constants.KEY_SP_ZOO_PLANTS_DATE_TIME)
            plantsMeta?.resources?.firstOrNull()?.resourceId?.let { plantsRid ->
                zooRepo.fetchPlants(plantsRid)
            }
        } catch (e: Exception) {
            Log.d(tagName, e.message , e)
        }
    }

    private suspend fun fetchAnimalsInfo(animalsQuery: String) {
        try {
            val animalMeta = getNewMeta(animalsQuery, Constants.KEY_SP_ZOO_ANIMALS_DATE_TIME)
            animalMeta?.resources?.firstOrNull()?.resourceId?.let { plantsRid ->
                zooRepo.fetchAnimals(plantsRid)
            }
        } catch (e: Exception) {
            Log.d(tagName, e.message , e)
        }
    }
}