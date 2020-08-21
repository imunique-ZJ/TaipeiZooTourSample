package zj.app.taipeizootour.viewmodel

import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import zj.app.taipeizootour.api.data.DataSetMetadata
import zj.app.taipeizootour.const.Constants
import zj.app.taipeizootour.db.data.AreaWithAnimals
import zj.app.taipeizootour.db.data.AreaWithPlants
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

    private val areaWithPlants = MutableLiveData<AreaWithPlants?>()
    private val areaWithAnimals = MutableLiveData<AreaWithAnimals?>()
    private val selectedPlantId = MutableLiveData<ZooPlant?>()

    val state = MutableLiveData<ZooAreaState>(ZooAreaState.Loading)
    val areaLiveData = zooRepo.getLiveArea()
    val areaWithPlantsLiveData: LiveData<AreaWithPlants?> = areaWithPlants
    val areaWithAnimalsLiveData: LiveData<AreaWithAnimals?> = areaWithAnimals
    val selectedPlantIdLiveData: LiveData<ZooPlant?> = selectedPlantId

    fun fetchData(areaIntroQuery: String, plantsQuery: String, animalsQuery: String) {
        viewModelScope.launch {
            state.value = ZooAreaState.Loading
            val areaIntroMeta = getNewMeta(areaIntroQuery, Constants.KEY_SP_ZOO_AREA_INTRO_DATE_TIME)
            val areaIntroRid = areaIntroMeta?.resources?.firstOrNull()?.resourceId
            if (areaIntroRid != null) {
                zooRepo.fetchAreaIntro(areaIntroMeta.id, areaIntroRid)
                val plantsMeta = getNewMeta(plantsQuery, Constants.KEY_SP_ZOO_PLANTS_DATE_TIME)
                plantsMeta?.resources?.firstOrNull()?.resourceId?.let { plantsRid ->
                    zooRepo.fetchPlants(plantsRid)
                }
                val animalMeta = getNewMeta(animalsQuery, Constants.KEY_SP_ZOO_ANIMALS_DATE_TIME)
                animalMeta?.resources?.firstOrNull()?.resourceId?.let { plantsRid ->
                    zooRepo.fetchAnimals(plantsRid)
                }
            }
            state.value = ZooAreaState.Finish
        }
    }

    fun fetchAreaPlants(areaId: Int) {
        viewModelScope.launch {
            areaWithPlants.value = zooRepo.getAreaPlants(areaId)
        }
    }

    fun fetchAreaAnimals(areaId: Int) {
        viewModelScope.launch {
            areaWithAnimals.value = zooRepo.getAreaAnimals(areaId)
        }
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
}